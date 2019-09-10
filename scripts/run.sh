#! /bin/bash

#项目名称
APP_NAME="监控服务"

#命令使用说明，给出提示
usage() {
    echo "Usage: sh run.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  get_pid
  if [[ -z "${pid}" ]]; then
   return 1
  else
   return 0
  fi
}

#获取pid
get_pid(){
  jar_name=`ls|grep *jar`
  pid=`ps -ef|grep ${jar_name}|grep -v grep|awk '{print $2}'`
}

#打印pid
print_pid(){
  get_pid
  echo "pid:${pid}"
}

#获取profile
get_profile(){
  case "${profile}" in
    "dev" )
      profile="dev"
      ;;
    "test" )
      profile="test"
      ;;
    "pro" )
      profile="pro"
      ;;
    *)
      profile=
      ;;
  esac
}

#启动
start(){
  is_exist
  if [[ $? -eq "0" ]]; then
    echo "${APP_NAME}已经启动，请勿重复启动！"
    print_pid
  else
    echo "开始启动${APP_NAME}"
    command="nohup java
    -Djava.rmi.server.hostname=192.168.178.184
    -Dcom.sun.management.jmxremote
    -Dcom.sun.management.jmxremote.port=18083
    -Dcom.sun.management.jmxremote.rmi.port=18083
    -Dcom.sun.management.jmxremote.ssl=false
    -Dcom.sun.management.jmxremote.authenticate=false
    -Xms50M -Xmx1000M
    -XX:+UseConcMarkSweepGC 
    -XX:+UseParNewGC 
    -XX:+HeapDumpOnOutOfMemoryError 
    -XX:+PrintGCDateStamps 
    -XX:+PrintGCDetails 
    -XX:+UseGCLogFileRotation 
    -XX:NumberOfGCLogFiles=10 
    -XX:GCLogFileSize=10M 
    -Xloggc:logs/gc.log 
    -verbose:gc 
    -Dlogging.config=config/log4j2-spring.xml "
    if [[ -n "${profile}" ]];then
      command="${command} -Dspring.profiles.active=${profile}"
    fi
    command="${command} -jar *.jar >/dev/null 2>&1&"
    echo ${command}
    eval ${command}
    is_exist
    while [[ $? -eq "1" ]];do
      is_exist
    done
    get_pid
    echo "${APP_NAME}启动完成，pid:${pid}"
  fi
}

#停止方法
stop(){
  is_exist
  if [[ $? -eq "0" ]]; then
    get_pid
    echo "开始停止${APP_NAME}，pid=${pid}."
    kill ${pid}
    is_exist
    while [[ $? -eq "0" ]];do
      is_exist
    done
    echo "${APP_NAME}已经停止"
  else
    echo "${APP_NAME}未启动，不用执行停止命令."
  fi
}

#运行状态
status(){
  is_exist
  if [[ $? -eq "0" ]]; then
    echo "${APP_NAME}运行中."
    print_pid
  else
    echo "${APP_NAME}未启动"
  fi
}

#重启
restart(){
  echo "重启${APP_NAME}......"
  is_exist
  if [[ $? -eq "0" ]]; then
     stop
  fi
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
profile="$2"
get_profile
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
