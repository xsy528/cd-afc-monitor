/*
 * 日期：2010-11-2
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.init;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.DicitemEntry;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;
import com.insigma.commons.dic.loader.IDicClassListProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Ticket: <br/>
 *
 * @author 邱昌进(qiuchangjin @ zdwxjd.com)
 */
public class DicOverwriteInitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DicOverwriteInitor.class);

    public static void init(IDicClassListProvider dicProvider) {
        List<Class<?>> dicAnnos = dicProvider.getDicClassList();
        LOGGER.info("共得到{}个字典项:{}", dicAnnos.size(), dicAnnos);

        Map<Class<?>, List<Class<?>>> map = new HashMap<>();
        //按照覆盖类分组
        for (Class<?> projectClazz : dicAnnos) {
            map.computeIfAbsent(projectClazz.getAnnotation(Dic.class).overClass(),
                    (key) -> new ArrayList<>()).add(projectClazz);
        }
        map.forEach((overClass, overClassList) -> {
            if (overClassList.size() > 2) {
                LOGGER.info("多个项目要覆盖产品字典:{},忽略。项目覆盖类为：{}", overClass, overClassList);
                return;
            }
            Class<?> projectClazz = null;
            if (overClassList.size() == 1) {
                LOGGER.info("没有项目要覆盖产品字典:{},初始化产品字典本身。", overClass);
                projectClazz = overClass;
            }

            //如果覆盖类和本类不一样，覆盖不一样的类
            for (Class<?> overClazz : overClassList) {
                if (!overClazz.equals(overClass)) {
                    projectClazz = overClazz;
                    break;
                }
            }
            LOGGER.info("项目字典[" + projectClazz + "],覆盖产品字典[" + overClass + "].");
            if (projectClazz == null) {
                return;
            }

            Object instance;
            Definition definition;
            try {
                Method getInstance = overClass.getMethod("getInstance");
                instance = getInstance.invoke(null);
                if (instance instanceof Definition) {
                    definition = (Definition) instance;
                } else {
                    return;
                }
            } catch (Exception e) {
                LOGGER.error(String.format("%s没有定义单例方法：getInstance(),无法进行覆盖操作，忽略。", overClass));
                return;
            }
            //获取保存字典数据的Map
            Map<String, DicitemEntry> dicItecEntryMap = definition.dicItecEntryMap;
            dicItecEntryMap.clear();
            if (!projectClazz.equals(overClass)) {
                // 如果不是自己，先设null
                for (Field productField : overClass.getFields()) {
                    // 先把所有的产品字典值设置为null
                    if (Modifier.isStatic(productField.getModifiers())
                            && !Modifier.isFinal(productField.getModifiers())) {
                        try {
                            productField.set(null, null);
                        } catch (IllegalAccessException e) {
                            LOGGER.error("设置null值异常", e);
                        }
                    }
                }
            }

            Field[] fields = projectClazz.getFields();
            for (Field projectField : fields) {
                String fname = projectField.getName();
                try {
                    if ("instance".equals(fname)) {
                        continue;
                    }
                    //只覆盖 私有的 数字和字符串变量
                    if (!projectField.getType().isPrimitive()
                            && !Number.class.isAssignableFrom(projectField.getType())
                            && !String.class.equals(projectField.getType())) {
                        //当前支持数字类型和字符串类型
                        continue;
                    }
                    Object projectValue = projectField.get(null);
                    DicItem dicitem = projectField.getAnnotation(DicItem.class);
                    if (dicitem != null) {
                        dicItecEntryMap.put(fname, new DicitemEntry(fname, dicitem, projectValue));
                    }

                    Field productField = overClass.getField(fname);
                    //不覆盖final类型
                    if (Modifier.isFinal(productField.getModifiers())) {
                        LOGGER.info(String.format("======字典类：[%s]中的字段:[%s]为final，无法覆盖，请检查=====",
                                overClass.getName(), productField.getName()));
                        continue;
                    }
                    //字段类型不一致
                    if (!projectField.getType().equals(productField.getType())) {
                        LOGGER.error("====字典类：产品类[{}]中的字段:[{}]类型和项目类[{}]中的字段:[{}]类型不一致，无法覆盖，" +
                                        "请检查====",
                                overClass.getName(), productField.getName(), projectClazz.getName(),
                                projectField.getName());
                        continue;
                    }/**/
                    // 覆盖产品级的常量值
                    productField.setAccessible(true);
                    productField.set(null, projectValue);
                } catch (SecurityException e) {
                    LOGGER.error("安全问题.", e);
                } catch (NoSuchFieldException e) {
                    LOGGER.debug(projectField + "在产品类:" + overClass.getName() + "中不存在。");
                    continue;
                } catch (Exception e) {
                    LOGGER.error("======处理项目字典类：[{}]的字段:[{}]时异常,要覆盖的产品字典为:[{}]=====",
                            projectClazz.getName(), fname, overClass.getName(), e);
                }
            }
            List<Object> codeList = new ArrayList<>(dicItecEntryMap.size());
            List<String> nameList = new ArrayList<>(dicItecEntryMap.size());
            //按照DicItem的index属性排序
            dicItecEntryMap.values().stream().sorted(DicitemEntry.DICCMPR).forEach(dicitemEntry -> {
                codeList.add(dicitemEntry.value);
                nameList.add(dicitemEntry.dicitem.name());
            });
            definition.setCodeList(codeList.toArray());
            definition.setNameList(nameList.toArray(new String[0]));
        });
    }

}
