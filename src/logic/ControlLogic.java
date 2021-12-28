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

public class ControlLogic {

    private List<Module> modules;
    private ModuleRepository moduleRepo;

    public ControlLogic() {
        this.modules = new ArrayList<>();
        this.moduleRepo = new ModuleRepository();
    }

    // Fill the domain container lists with instances, retrieved and created in the
    // repository's
    private void retrieveData() {
        this.modules = this.moduleRepo.retrieve().stream().filter(Module.class::isInstance)
                .map(Module.class::cast).collect(Collectors.toList());
    }

    public Map<String, Integer> getModuleNamesVersionsAndIDs() {

        HashMap<String, Integer> mapToReturn = this.moduleRepo.getAllModuleNames();
        return mapToReturn;
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
        this.moduleRepo.insert((Object) module);

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
