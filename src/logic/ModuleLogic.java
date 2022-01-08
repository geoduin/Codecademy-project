package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import database.ModuleRepository;
import domain.Module;
import domain.Course;
import domain.*;

public class ModuleLogic {

    private List<Module> modules;
    private ModuleRepository moduleRepo;

    public ModuleLogic() {
        this.modules = new ArrayList<>();
        this.moduleRepo = new ModuleRepository();
    }

    // Retrieve formatted strings representing each existing modules, coupled with
    // their ID via a map
    public HashMap<String, Integer> getModuleNamesVersionsAndIDs() {
        // Argument set false, to not make the repo method filter the results.
        return this.moduleRepo.getAllModuleNames(false);
    }

    // Addable module means a module that has no relation with a Course yet. Use
    // same format as previous method
    public HashMap<String, Integer> getAddableModules() {
        // Argument set true, to not make the repo method filter the results to only
        // modules that have no Course assigned.
        return this.moduleRepo.getAllModuleNames(true);
    }

    public void unlinkModuleWithCourse(int id) {
        this.moduleRepo.unassignModuleToCourse(id);
        this.moduleRepo.deleteProgressWithoutCourse();
    }

    public void linkModuleWithCourse(String courseName, int id) {
        this.moduleRepo.assignModuleToCourse(courseName, id);
    }

    public HashMap<String, Integer> getModulesWithinCourse(String courseName) {
        return this.moduleRepo.getAllModuleNames(courseName);
    }

    public Module retrieveModuleByID(int id) {
        return this.moduleRepo.retrieveModuleByID(id);
    }

    public List<Module> getModules() {
        return modules;
    }

    // Uses the overloaded constructor of Module to add a new one to the list.
    // Consequentially saves it in the database
    public void newModule(Status status, String title, int version, int trackingNumber,
            String description, String contactName, String emailAddress) {
        Module module = new Module(status, title, version, trackingNumber, description, contactName,
                emailAddress);
        this.modules.add(module);
        this.moduleRepo.insert(module);

    }

    // Delete a module, if exists. Returns true if succesfull and aks repo to delete
    // from DB
    public boolean deleteModule(int id) {
        this.moduleRepo.delete(id);
        return true;
    }

    // Method to edit the editable fields in a module. Gets instantly saved in the
    // database
    public void editModule(String email, String contact, String description, String status, int positionInCourse,
            Module module) {

        // Alterable information
        module.setEmailAddress(email);
        module.setContactName(contact);
        module.setDescription(description);

        Status statusEnum;
        if (status.equals("ACTIVE")) {
            statusEnum = domain.Status.ACTIVE;
        } else if (status.equals("ARCHIVED")) {
            statusEnum = domain.Status.ARCHIVED;
        } else {
            statusEnum = domain.Status.CONCEPT;
        }

        module.setStatus(statusEnum);
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
}
