package com.bot.api.methods;



public abstract interface Filter<F>
{
  public abstract boolean accept(F paramF);
}