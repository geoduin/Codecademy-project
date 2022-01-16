package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.EnrollmentRepository;
import database.ModuleRepository;
import domain.Module;
import domain.*;

/*
*Class responsible for communication between the GUI and the module repository. It *collects all the functionality that can actually change something the modules.  
*/
public class ModuleLogic {

    private List<Module> modules;
    private ModuleRepository moduleRepo;
    private EnrollmentRepository enrollmentRepo;

    public ModuleLogic() {
        this.modules = new ArrayList<>();
        this.moduleRepo = new ModuleRepository();
        this.enrollmentRepo = new EnrollmentRepository();
    }

    // Retrieve formatted strings representing each existing modules, coupled with
    // their ID via a map
    public Map<String, Integer> getModuleNamesVersionsAndIDs() {
        // Argument set false, to not make the repo method filter the results.
        return this.moduleRepo.getAllModuleNames(false);
    }

    // Addable module means a module that has no relation with a Course yet. Use
    // same format as previous method
    public Map<String, Integer> getAddableModules() {
        // Argument set true, to not make the repo method filter the results to only
        // modules that have no Course assigned.
        return this.moduleRepo.getAllModuleNames(true);
    }

    // Based on the database attribute 'id' of a module, unlink any course that is
    // linked with it
    public void unlinkModuleWithCourse(int id) {
        this.moduleRepo.unassignModuleToCourse(id);
        this.enrollmentRepo.deleteProgressWithoutCourse();
    }

    // Based on the database attribute 'id' of a module and a coursename, link them
    // together
    public void linkModuleWithCourse(String courseName, int id) {
        this.moduleRepo.assignModuleToCourse(courseName, id);
        this.enrollmentRepo.updateProgressWithNewModule(courseName, id);
    }

    // Retrieving modules that are linked to the courseName received as argument.
    // Using a map so that the unique attributes of module (title and version) can
    // be shown to the user, while also having the ID linked with that string
    public Map<String, Integer> getModulesWithinCourse(String courseName) {
        return this.moduleRepo.getAllModuleNames(courseName);
    }

    // Instantiate a module from the database, finding it by its database ID
    public Module retrieveModuleByID(int id) {
        return this.moduleRepo.retrieveModuleByID(id);
    }

    // Regular getter
    public List<Module> getModules() {
        return modules;
    }

    // Uses the overloaded constructor of Module to add a new one to the list.
    // Consequentially saves it in the database
    public void newModule(Status status, String title, int version, int trackingNumber,
            String description, String contactName, String emailAddress, int contentID) {
        Module module = new Module(status, title, version, trackingNumber, description, contactName,
                emailAddress, null, contentID);
        this.modules.add(module);
        this.moduleRepo.insert(module);

    }

    // Delete a module, if exists. Returns true if successful and aks repo to delete
    // from DB
    public boolean deleteModule(int id) {
        return this.moduleRepo.delete(id);
    }

    // Method to edit the editable fields in a module. Gets instantly saved in the
    // database
    public void editModule(String email, String contact, String description, Status status, int positionInCourse,
            Module module) {

        // Alterable information
        module.setEmailAddress(email);
        module.setContactName(contact);
        module.setDescription(description);
        module.setStatus(status);
        module.setPositionWithinCourse(positionInCourse);
        moduleRepo.update(module);
    }

    // A module is unique by its title and version combination. HashCode is not used
    // for check-if-exists, to prevent having to instantiate the module immediately
    public boolean moduleAlreadyExistsBasedOn(String title, int version) {
        for (Module module : this.modules) {
            if (module.getTitle().equals(title) && module.getVersion() == version) {
                return true;
            }
        }

        return false;
    }

    // Instantiate all modules, existing in the database
    public ArrayList<Module> retrieveAllModules() {
        return this.moduleRepo.retrieve();
    }
}
