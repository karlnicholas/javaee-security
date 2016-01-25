package jsec.controller.user;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import jsec.facade.UserFacade;
import jsec.model.User;

/**
 * Controller for logging in a user from email and password fields.
 * 
 * @author Karl Nicholas
 *
 */
@Model
public class Login {
    private String email;
    private String password;

    @Inject FacesContext context;
    @Inject UserFacade userFacade;

    /**
     * Login a user using the email and password fields.
     * 
     * @return String navigation to /views/account.xhtml
     */
    public String login() {
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        if ( request.getUserPrincipal() == null ) {
            try {
                request.login(email, password);
                User user = userFacade.findByEmail(email);
                externalContext.getSessionMap().put("user", user);                
            } catch (ServletException ignored) {
                // Handle unknown username/password in request.login().
                context.addMessage(null, new FacesMessage("Login Failed!", ""));
                return null;
            }
        } 
        return "/views/account.xhtml?faces-redirect=true";
    }

    /**
     * Email Field
     * @return Email field.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email Field
     * @param email field.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Password Field
     * @return Password field.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Password Field
     * @param password field.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}