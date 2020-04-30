package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable{
	private int startCycle;
	private Rescuable target;
	private boolean active;
	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getStartCycle() {
		return startCycle;
	}
	public Rescuable getTarget() {
		return target;
	}
	public void strike() throws CitizenAlreadyDeadException, BuildingAlreadyCollapsedException 
	{
		if(target instanceof Citizen) {
			Citizen e=(Citizen)target;
				if(e.getState()== CitizenState.DECEASED)
					throw new CitizenAlreadyDeadException(this);
				else {
					target.struckBy(this);
					active=true;
			}
		}
		else {
			ResidentialBuilding e=(ResidentialBuilding)target;
			if(e.getStructuralIntegrity()== 0)
				throw new BuildingAlreadyCollapsedException(this);
			else {
				target.struckBy(this);
				active=true;
		}
		}
	}
   public String getname() {
	   if(this instanceof Fire)return "Fire";
	   if(this instanceof Collapse)return "Collapse";
	   if(this instanceof GasLeak)return "GasLeak";
	   if(this instanceof Infection)return "Infection";
	   if(this instanceof Injury)return "Injury";
	   return "Disaster";
	   
   }
}
