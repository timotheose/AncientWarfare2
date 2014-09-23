package net.shadowmage.ancientwarfare.automation.tile.torque;

import net.minecraftforge.common.util.ForgeDirection;
import net.shadowmage.ancientwarfare.automation.proxy.BCProxy;
import net.shadowmage.ancientwarfare.core.interfaces.ITorque;
import net.shadowmage.ancientwarfare.core.interfaces.ITorque.ITorqueTransport;
import buildcraft.api.mj.IBatteryObject;
import buildcraft.api.mj.ISidedBatteryProvider;
import buildcraft.api.power.IPowerEmitter;
import cpw.mods.fml.common.Optional;

@Optional.InterfaceList(value=
  {
  @Optional.Interface(iface="buildcraft.api.power.IPowerEmitter",modid="BuildCraft|Core",striprefs=true),
  @Optional.Interface(iface="buildcraft.api.mj.ISidedBatteryProvider",modid="BuildCraft|Core",striprefs=true)
  })
public abstract class TileTorqueTransportBase extends TileTorqueBase implements ITorqueTransport, IPowerEmitter, ISidedBatteryProvider
{

@Override
public void updateEntity()
  {
  if(worldObj.isRemote)
    {
    this.clientNetworkUpdate();
    return;
    }
  else
    {
    this.serverNetworkUpdate();
    }
  this.energyInput = this.storedEnergy - this.prevEnergy;
  double s = this.storedEnergy;
  ITorque.transferPower(worldObj, xCoord, yCoord, zCoord, this);
  this.energyOutput = s - this.storedEnergy;
  ITorque.applyPowerDrain(this);
  this.prevEnergy = this.storedEnergy;
  }

@Override
public double addEnergy(ForgeDirection from, double energy)
  {
  if(canInput(from))
    {
    if(energy+getEnergyStored()>getMaxEnergy())
      {
      energy = getMaxEnergy()-getEnergyStored();
      }
    if(energy>getMaxInput())
      {
      energy = getMaxInput();
      }
    setEnergy(getEnergyStored()+energy);
    return energy;    
    }
  return 0;
  }

@Override
public double getMaxOutput()
  {
  return maxOutput;
  }

@Override
public double getMaxInput()
  {
  return maxInput;
  }

@Override
public boolean canInput(ForgeDirection from)
  {
  return from!=orientation;
  }

@Override
public boolean canOutput(ForgeDirection towards)
  {
  return towards==orientation;
  }

@Optional.Method(modid="BuildCraft|Core")
@Override
public boolean canEmitPowerFrom(ForgeDirection side)
  {
  return canOutput(side);
  }

@Optional.Method(modid="BuildCraft|Core")
@Override
public IBatteryObject getMjBattery(String kind, ForgeDirection direction)
  {  
  return (IBatteryObject) BCProxy.instance.getBatteryObject(kind, this, direction);
  }

}
