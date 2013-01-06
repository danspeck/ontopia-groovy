package net.ontopia.groovy

import net.ontopia.topicmaps.core.OccurrenceIF
import net.ontopia.infoset.core.LocatorIF
import net.ontopia.topicmaps.core.DataTypes

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Occurrence {
   @Delegate OccurrenceIF delegate

   Occurrence(occ) {
     this.delegate = occ
   }

   Object asDate() {
     def datatype = delegate.getDataType()
     def strval = delegate.getValue()

     switch(datatype) {

     case { DataTypes.TYPE_DATE } : 
       try { 
          def thedate = Date.parse("yyyy-MM-dd",strval)
          return thedate
       }
       catch ( Exception e1 ) {
         try { 
           return Date.parse("yyyy-MM",strval)
         }
         catch ( Exception e2 ) {
           try { 
             return Date.parse("yyyy",strval)
           }
           catch ( Exception e3 ) {
             return "0000-00-00"
           }
         }
       }
     default : return strval
     }
   }
}