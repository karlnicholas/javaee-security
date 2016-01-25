package jsec.controller.user;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import jsec.facade.RoleFacade;
import jsec.facade.UserFacade;
import jsec.model.User;

/**
 * Controller to update User information.
 * 
 * @author Karl Nicholas
 *
 */
@Model
public class Admin extends Principal {
    
    @Inject UserFacade userFacade;
    @Inject RoleFacade roleFacade;

    /**
     * Return a list of users
     * @return List<User> users
     */
    public List<User> getUsers() {
        return userFacade.findAll();
    }

    /**
     * Remove a user based on id.
     * @param id of User to be removed.
     */
    public void removeUser(Long id) {
        if ( getUser().getId() == id ) throw new RuntimeException("Cannot change current user!");
        userFacade.delete(id);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User removed", "") );
    }

    /**
     * Promote user to administrator based on id
     * @param id of User to be promoted.
     */
    public void promoteUser(Long id) {
        if ( getUser().getId() == id ) return;
        userFacade.promoteUser(id);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User promoted to administrator", "") );
    }

    /**
     * Demote a user, based on id, by removing ADMIN role.
     * @param id of User to be demoted. 
     */
    public void demoteUser(Long id) {
        if ( getUser().getId() == id ) return;
        userFacade.demoteUser(id);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User demoted to user only", "") );
    }

}