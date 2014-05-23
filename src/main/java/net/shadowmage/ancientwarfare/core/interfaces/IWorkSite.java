package net.shadowmage.ancientwarfare.core.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.shadowmage.ancientwarfare.core.util.BlockPosition;

public interface IWorkSite
{

/**
 * workers should call this before calling doWork() to make sure that the site
 * actually has work to do.
 * @return
 */
public boolean hasWork();

/**
 * can be called by a worker if hasWork() returns true.
 * @param worker
 */
public void addEnergyFromWorker(IWorker worker);

public void addEnergyFromPlayer(EntityPlayer player);

/**
 * called by workers to validate work-type when IWorker.canWorkAt(IWorkSite) is called
 * workers should be responsible for maintaining their own list of acceptable work types
 * @return
 */
public WorkType getWorkType();

public Team getTeam();

public BlockPosition getWorkBoundsMin();

public BlockPosition getWorkBoundsMax();

public boolean hasWorkBounds();

public static enum WorkType
{
MINING,
FARMING,
FORESTRY,
CRAFTING,
RESEARCH,
NONE;
}

}
