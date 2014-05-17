package com.page5of4.codon;

public enum AutomaticallySubscribe {
   DEFAULT,
   NEVER,
   ALWAYS;

   public AutomaticallySubscribe or(AutomaticallySubscribe subscribe) {
      if(subscribe == DEFAULT) {
         return this;
      }
      return subscribe;
   }

   public boolean shouldSubscribe() {
      switch(this) {
      case DEFAULT:
         return true;
      case NEVER:
         return false;
      case ALWAYS:
         return true;
      }
      return true;
   }
}
