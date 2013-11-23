package tw.jiangsir.Utils.Interfaces;

import javax.servlet.http.HttpServletRequest;

import tw.jiangsir.Utils.Exceptions.AccessException;

/**
 * @author jiangsir
 * 
 */
public interface IAccessible {
    public boolean accessible(HttpServletRequest request)
	    throws AccessException;
}
