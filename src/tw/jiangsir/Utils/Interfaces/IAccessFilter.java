package tw.jiangsir.Utils.Interfaces;

import javax.servlet.http.HttpServletRequest;

import tw.jiangsir.Utils.Exceptions.AccessException;

/**
 * @author jiangsir
 * 
 */
public interface IAccessFilter {
    public boolean accessible(HttpServletRequest request)
	    throws AccessException;
}
