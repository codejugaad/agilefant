package fi.hut.soberit.agilefant.transfer;

import fi.hut.soberit.agilefant.model.Project;
import fi.hut.soberit.agilefant.util.BeanCopier;

public class ProjectTO extends Project {

    private ScheduleStatus scheduleStatus;
    
    public ProjectTO(Project project) {
        BeanCopier.copy(project, this);
    }
    
    public void setScheduleStatus(ScheduleStatus scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public ScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

}