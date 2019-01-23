package com.insigma.acc.monitor.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.acc.monitor.controller.resolver.CustomMethodNameResolver;
import com.insigma.acc.monitor.exception.ErrorCode;
import com.insigma.acc.monitor.model.dto.Result;
import com.insigma.acc.monitor.util.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.LastModified;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2018-12-24:10:34
 */
public abstract class BaseMultiActionController extends AbstractController implements LastModified {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMultiActionController.class);

    protected static Map<String,String> methodMapping = new HashMap<>();

    /** Suffix for last-modified methods */
    public static final String LAST_MODIFIED_METHOD_SUFFIX = "LastModified";

    /** Default command name used for binding command objects: "command" */
    public static final String DEFAULT_COMMAND_NAME = "command";

    /**
     * Log category to use when no mapped handler is found for a request.
     * @see #pageNotFoundLogger
     */
    public static final String PAGE_NOT_FOUND_LOG_CATEGORY = "org.springframework.web.servlet.PageNotFound";


    /**
     * Additional logger to use when no mapped handler is found for a request.
     * @see #PAGE_NOT_FOUND_LOG_CATEGORY
     */
    protected static final Log pageNotFoundLogger = LogFactory.getLog(PAGE_NOT_FOUND_LOG_CATEGORY);

    /** Object we'll invoke methods on. Defaults to this. */
    private Object delegate;

    /** Delegate that knows how to determine method names from incoming requests */
    private MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();

    /** List of Validators to apply to commands */
    private Validator[] validators;

    /** Optional strategy for pre-initializing data binding */
    private WebBindingInitializer webBindingInitializer;

    /** Handler methods, keyed by name */
    private final Map handlerMethodMap = new HashMap();

    /** LastModified methods, keyed by handler method name (without LAST_MODIFIED_SUFFIX) */
    private final Map lastModifiedMethodMap = new HashMap();

    /** Methods, keyed by exception class */
    private final Map exceptionHandlerMap = new HashMap();


    /**
     * Constructor for <code>MultiActionController</code> that looks for
     * handler methods in the present subclass.
     */
    public BaseMultiActionController() {
        this.delegate = this;
        registerHandlerMethods(this.delegate);
        this.setMethodNameResolver(new CustomMethodNameResolver(methodMapping));
        try {
            this.registerExceptionHandlerMethod(BaseMultiActionController.class.getDeclaredMethod("parseError",
                    HttpServletRequest.class, HttpServletResponse.class, Throwable.class));
        } catch (NoSuchMethodException e) {
            LOGGER.error("未找到默认方法",e);
        }
        // We'll accept no handler methods found here - a delegate might be set later on.
    }

    /**
     * Set the delegate used by this class; the default is <code>this</code>,
     * assuming that handler methods have been added by a subclass.
     * <p>This method does not get invoked once the class is configured.
     * @param delegate an object containing handler methods
     * @throws IllegalStateException if no handler methods are found
     */
    public final void setDelegate(Object delegate) {
        Assert.notNull(delegate, "Delegate must not be null");
        this.delegate = delegate;
        registerHandlerMethods(this.delegate);
        // There must be SOME handler methods.
        if (this.handlerMethodMap.isEmpty()) {
            throw new IllegalStateException("No handler methods in class [" + this.delegate.getClass() + "]");
        }
    }

    /**
     * Set the method name resolver that this class should use.
     * <p>Allows parameterization of handler method mappings.
     */
    public final void setMethodNameResolver(MethodNameResolver methodNameResolver) {
        this.methodNameResolver = methodNameResolver;
    }

    /**
     * Return the MethodNameResolver used by this class.
     */
    public final MethodNameResolver getMethodNameResolver() {
        return this.methodNameResolver;
    }

    /**
     * Set the {@link Validator Validators} for this controller.
     * <p>The <code>Validators</code> must support the specified command class.
     */
    public final void setValidators(Validator[] validators) {
        this.validators = validators;
    }

    /**
     * Return the Validators for this controller.
     */
    public final Validator[] getValidators() {
        return this.validators;
    }

    /**
     * Specify a WebBindingInitializer which will apply pre-configured
     * configuration to every DataBinder that this controller uses.
     * <p>Allows for factoring out the entire binder configuration
     * to separate objects, as an alternative to {@link #initBinder}.
     */
    public final void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    /**
     * Return the WebBindingInitializer (if any) which will apply pre-configured
     * configuration to every DataBinder that this controller uses.
     */
    public final WebBindingInitializer getWebBindingInitializer() {
        return this.webBindingInitializer;
    }


    /**
     * Registers all handlers methods on the delegate object.
     */
    private void registerHandlerMethods(Object delegate) {
        this.handlerMethodMap.clear();
        this.lastModifiedMethodMap.clear();
        this.exceptionHandlerMap.clear();

        // Look at all methods in the subclass, trying to find
        // methods that are validators according to our criteria
        Method[] methods = delegate.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            // We're looking for methods with given parameters.
            Method method = methods[i];
            if (isExceptionHandlerMethod(method)) {
                registerExceptionHandlerMethod(method);
            }
            else if (isHandlerMethod(method)) {
                registerHandlerMethod(method);
                registerLastModifiedMethodIfExists(delegate, method);
            }
        }
    }

    /**
     * Is the supplied method a valid handler method?
     * <p>Does not consider <code>Controller.handleRequest</code> itself
     * as handler method (to avoid potential stack overflow).
     */
    private boolean isHandlerMethod(Method method) {
        Class returnType = method.getReturnType();
        if (ModelAndView.class.equals(returnType) || Map.class.equals(returnType) || String.class.equals(returnType) ||
                void.class.equals(returnType)|| Result.class.equals(returnType)) {
            Class[] parameterTypes = method.getParameterTypes();
            return !("handleRequest".equals(method.getName()) && parameterTypes.length == 2);
//            return (parameterTypes.length >= 2 &&
//                    HttpServletRequest.class.equals(parameterTypes[0]) &&
//                    HttpServletResponse.class.equals(parameterTypes[1]) &&
//                    !("handleRequest".equals(method.getName()) && parameterTypes.length == 2));
        }
        return false;
    }

    /**
     * Is the supplied method a valid exception handler method?
     */
    private boolean isExceptionHandlerMethod(Method method) {
        return (isHandlerMethod(method) &&
                method.getParameterTypes().length == 3 &&
                Throwable.class.isAssignableFrom(method.getParameterTypes()[2]));
    }

    /**
     * Registers the supplied method as a request handler.
     */
    private void registerHandlerMethod(Method method) {
        if (logger.isDebugEnabled()) {
            logger.debug("Found action method [" + method + "]");
        }
        this.handlerMethodMap.put(method.getName(), method);
    }

    /**
     * Registers a last-modified handler method for the supplied handler method
     * if one exists.
     */
    private void registerLastModifiedMethodIfExists(Object delegate, Method method) {
        // Look for corresponding LastModified method.
        try {
            Method lastModifiedMethod = delegate.getClass().getMethod(
                    method.getName() + LAST_MODIFIED_METHOD_SUFFIX,
                    new Class[] {HttpServletRequest.class});
            Class returnType = lastModifiedMethod.getReturnType();
            if (!(long.class.equals(returnType) || Long.class.equals(returnType))) {
                throw new IllegalStateException("last-modified method [" + lastModifiedMethod +
                        "] declares an invalid return type - needs to be 'long' or 'Long'");
            }
            // Put in cache, keyed by handler method name.
            this.lastModifiedMethodMap.put(method.getName(), lastModifiedMethod);
            if (logger.isDebugEnabled()) {
                logger.debug("Found last-modified method for handler method [" + method + "]");
            }
        }
        catch (NoSuchMethodException ex) {
            // No last modified method. That's ok.
        }
    }

    /**
     * Registers the supplied method as an exception handler.
     */
    private void registerExceptionHandlerMethod(Method method) {
        this.exceptionHandlerMap.put(method.getParameterTypes()[2], method);
        if (logger.isDebugEnabled()) {
            logger.debug("Found exception handler method [" + method + "]");
        }
    }


    //---------------------------------------------------------------------
    // Implementation of LastModified
    //---------------------------------------------------------------------

    /**
     * Try to find an XXXXLastModified method, where XXXX is the name of a handler.
     * Return -1 if there's no such handler, indicating that content must be updated.
     * @see org.springframework.web.servlet.mvc.LastModified#getLastModified(HttpServletRequest)
     */
    public long getLastModified(HttpServletRequest request) {
        try {
            String handlerMethodName = this.methodNameResolver.getHandlerMethodName(request);
            Method lastModifiedMethod = (Method) this.lastModifiedMethodMap.get(handlerMethodName);
            if (lastModifiedMethod != null) {
                try {
                    // Invoke the last-modified method...
                    Long wrappedLong = (Long) lastModifiedMethod.invoke(this.delegate, new Object[] {request});
                    return (wrappedLong != null ? wrappedLong.longValue() : -1);
                }
                catch (Exception ex) {
                    // We encountered an error invoking the last-modified method.
                    // We can't do anything useful except log this, as we can't throw an exception.
                    logger.error("Failed to invoke last-modified method", ex);
                }
            }
        }
        catch (NoSuchRequestHandlingMethodException ex) {
            // No handler method for this request. This shouldn't happen, as this
            // method shouldn't be called unless a previous invocation of this class
            // has generated content. Do nothing, that's OK: We'll return default.
        }
        return -1L;
    }


    //---------------------------------------------------------------------
    // Implementation of AbstractController
    //---------------------------------------------------------------------

    /**
     * Determine a handler method and invoke it.
     * @see MethodNameResolver#getHandlerMethodName
     * @see #invokeNamedMethod
     * @see #handleNoSuchRequestHandlingMethod
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            String methodName = this.methodNameResolver.getHandlerMethodName(request);
            return invokeNamedMethod(methodName, request, response);
        }
        catch (NoSuchRequestHandlingMethodException ex) {
            return handleNoSuchRequestHandlingMethod(ex, request, response);
        }
    }

    /**
     * Handle the case where no request handler method was found.
     * <p>The default implementation logs a warning and sends an HTTP 404 error.
     * Alternatively, a fallback view could be chosen, or the
     * NoSuchRequestHandlingMethodException could be rethrown as-is.
     * @param ex the NoSuchRequestHandlingMethodException to be handled
     * @param request current HTTP request
     * @param response current HTTP response
     * @return a ModelAndView to render, or <code>null</code> if handled directly
     * @throws Exception an Exception that should be thrown as result of the servlet request
     */
    protected ModelAndView handleNoSuchRequestHandlingMethod(
            NoSuchRequestHandlingMethodException ex, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        pageNotFoundLogger.warn(ex.getMessage());
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

    /**
     * Invokes the named method.
     * <p>Uses a custom exception handler if possible; otherwise, throw an
     * unchecked exception; wrap a checked exception or Throwable.
     */
    protected final ModelAndView invokeNamedMethod(
            String methodName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Method method = (Method) this.handlerMethodMap.get(methodName);
        if (method == null) {
            throw new NoSuchRequestHandlingMethodException(methodName, getClass());
        }

        try {
            Class[] paramTypes = method.getParameterTypes();
            List params = new ArrayList(4);
            if (paramTypes.length!=0){
                params.add(request);
                params.add(response);
            }

            if (paramTypes.length >= 3 && paramTypes[2].equals(HttpSession.class)) {
                HttpSession session = request.getSession(false);
                if (session == null) {
                    throw new HttpSessionRequiredException(
                            "Pre-existing session required for handler method '" + methodName + "'");
                }
                params.add(session);
            }

            // If last parameter isn't of HttpSession type, it's a command.
            if (paramTypes.length >= 3 &&
                    !paramTypes[paramTypes.length - 1].equals(HttpSession.class)) {
                Object command = newCommandObject(paramTypes[paramTypes.length - 1]);
                params.add(command);
                bind(request, command);
            }

            Object returnValue = method.invoke(this.delegate, params.toArray(new Object[params.size()]));
            return massageReturnValueIfNecessary(returnValue,response,method);
        }
        catch (InvocationTargetException ex) {
            // The handler method threw an exception.
            return handleException(request, response, ex.getTargetException());
        }
        catch (Exception ex) {
            // The binding process threw an exception.
            return handleException(request, response, ex);
        }
    }

    /**
     * Processes the return value of a handler method to ensure that it either returns
     * <code>null</code> or an instance of {@link ModelAndView}. When returning a {@link Map},
     * the {@link Map} instance is wrapped in a new {@link ModelAndView} instance.
     */
    private ModelAndView massageReturnValueIfNecessary(Object returnValue,HttpServletResponse response,Method method) {
        if (returnValue instanceof ModelAndView) {
            return (ModelAndView) returnValue;
        }
        else if (returnValue instanceof Map) {
            return new ModelAndView().addAllObjects((Map) returnValue);
        }
        else if (returnValue instanceof String) {
            return new ModelAndView((String) returnValue);
        }
        else {
            if (returnValue!=null) {
                // 默认统一返回json数据
                response.setContentType("application/json; charset=utf-8");
                try (PrintWriter printWriter = response.getWriter()) {
                    JsonView jsonView = method.getAnnotation(JsonView.class);
                    String result;
                    if (jsonView!=null&&jsonView.value()!=null){
                        result = JsonUtils.parseObject(returnValue,jsonView.value()[0]);
                    }else{
                        result = JsonUtils.parseObject(returnValue);
                    }
                    printWriter.println(result);
                } catch (IOException e) {
                    LOGGER.error("写入response异常", e);
                }
            }
            return null;
        }
    }


    /**
     * Create a new command object of the given class.
     * <p>This implementation uses <code>BeanUtils.instantiateClass</code>,
     * so commands need to have public no-arg constructors.
     * Subclasses can override this implementation if desired.
     * @throws Exception if the command object could not be instantiated
     * @see org.springframework.beans.BeanUtils#instantiateClass(Class)
     */
    protected Object newCommandObject(Class clazz) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating new command of class [" + clazz.getName() + "]");
        }
        return BeanUtils.instantiateClass(clazz);
    }

    /**
     * Bind request parameters onto the given command bean
     * @param request request from which parameters will be bound
     * @param command command object, that must be a JavaBean
     * @throws Exception in case of invalid state or arguments
     */
    protected void bind(HttpServletRequest request, Object command) throws Exception {
        logger.debug("Binding request parameters onto MultiActionController command");
        ServletRequestDataBinder binder = createBinder(request, command);
        binder.bind(request);
        if (this.validators != null) {
            for (int i = 0; i < this.validators.length; i++) {
                if (this.validators[i].supports(command.getClass())) {
                    ValidationUtils.invokeValidator(this.validators[i], command, binder.getBindingResult());
                }
            }
        }
        binder.closeNoCatch();
    }

    /**
     * Create a new binder instance for the given command and request.
     * <p>Called by <code>bind</code>. Can be overridden to plug in custom
     * ServletRequestDataBinder subclasses.
     * <p>The default implementation creates a standard ServletRequestDataBinder,
     * and invokes <code>initBinder</code>. Note that <code>initBinder</code>
     * will not be invoked if you override this method!
     * @param request current HTTP request
     * @param command the command to bind onto
     * @return the new binder instance
     * @throws Exception in case of invalid state or arguments
     * @see #bind
     * @see #initBinder
     */
    protected ServletRequestDataBinder createBinder(HttpServletRequest request, Object command) throws Exception {
        ServletRequestDataBinder binder = new ServletRequestDataBinder(command, getCommandName(command));
        initBinder(request, binder);
        return binder;
    }

    /**
     * Return the command name to use for the given command object.
     * <p>Default is "command".
     * @param command the command object
     * @return the command name to use
     * @see #DEFAULT_COMMAND_NAME
     */
    protected String getCommandName(Object command) {
        return DEFAULT_COMMAND_NAME;
    }

    /**
     * Initialize the given binder instance, for example with custom editors.
     * Called by <code>createBinder</code>.
     * <p>This method allows you to register custom editors for certain fields of your
     * command class. For instance, you will be able to transform Date objects into a
     * String pattern and back, in order to allow your JavaBeans to have Date properties
     * and still be able to set and display them in an HTML interface.
     * <p>The default implementation is empty.
     * <p>Note: the command object is not directly passed to this method, but it's available
     * via {@link org.springframework.validation.DataBinder#getTarget()}
     * @param request current HTTP request
     * @param binder new binder instance
     * @throws Exception in case of invalid state or arguments
     * @see #createBinder
     * @see org.springframework.validation.DataBinder#registerCustomEditor
     * @see org.springframework.beans.propertyeditors.CustomDateEditor
     */
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        if (this.webBindingInitializer != null) {
            this.webBindingInitializer.initBinder(binder, new ServletWebRequest(request));
        }
        initBinder((ServletRequest) request, binder);
    }

    /**
     * Initialize the given binder instance, for example with custom editors.
     * @deprecated as of Spring 2.0:
     * use <code>initBinder(HttpServletRequest, ServletRequestDataBinder)</code> instead
     */
    protected void initBinder(ServletRequest request, ServletRequestDataBinder binder) throws Exception {
    }


    /**
     * Determine the exception handler method for the given exception.
     * <p>Can return <code>null</code> if not found.
     * @return a handler for the given exception type, or <code>null</code>
     * @param exception the exception to handle
     */
    protected Method getExceptionHandler(Throwable exception) {
        Class exceptionClass = exception.getClass();
        if (logger.isDebugEnabled()) {
            logger.debug("Trying to find handler for exception class [" + exceptionClass.getName() + "]");
        }
        Method handler = (Method) this.exceptionHandlerMap.get(exceptionClass);
        while (handler == null && !exceptionClass.equals(Throwable.class)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Trying to find handler for exception superclass [" + exceptionClass.getName() + "]");
            }
            exceptionClass = exceptionClass.getSuperclass();
            handler = (Method) this.exceptionHandlerMap.get(exceptionClass);
        }
        return handler;
    }

    /**
     * We've encountered an exception thrown from a handler method.
     * Invoke an appropriate exception handler method, if any.
     * @param request current HTTP request
     * @param response current HTTP response
     * @param ex the exception that got thrown
     * @return a ModelAndView to render the response
     */
    private ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Throwable ex)
            throws Exception {

        Method handler = getExceptionHandler(ex);
        if (handler != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invoking exception handler [" + handler + "] for exception: " + ex);
            }
            try {
                Object returnValue = handler.invoke(this.delegate, new Object[] {request, response, ex});
                return massageReturnValueIfNecessary(returnValue,response,handler);
            }
            catch (InvocationTargetException ex2) {
                logger.error("Original exception overridden by exception handling failure", ex);
                ReflectionUtils.rethrowException(ex2.getTargetException());
            }
            catch (Exception ex2) {
                logger.error("Failed to invoke exception handler method", ex2);
            }
        }
        else {
            // If we get here, there was no custom handler or we couldn't invoke it.
            ReflectionUtils.rethrowException(ex);
        }
        throw new IllegalStateException("Should never get here");
    }

    //默认异常处理
    protected Result<String> parseError(HttpServletRequest request, HttpServletResponse response, Throwable ex){
        LOGGER.error("捕获未处理异常",ex);
        return Result.error(ErrorCode.UNKNOW_ERROR);
    }
}
