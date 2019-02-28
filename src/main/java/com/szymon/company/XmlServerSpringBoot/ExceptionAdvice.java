
package com.szymon.company.XmlServerSpringBoot;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author szymon
 */
@ControllerAdvice
public class ExceptionAdvice {
    
    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public String unsupportedMediaHandler(HttpMediaTypeNotSupportedException ex) {
    return ex.getMessage()+". Supported content type: "+ex.getSupportedMediaTypes();
    }
    
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalArgumentHandler(IllegalArgumentException ex) {
    return ex.getMessage();
    }
}
