package net.ontopia.groovy

import net.ontopia.topicmaps.core.TMObjectIF
import net.ontopia.topicmaps.core.TopicIF
import net.ontopia.topicmaps.core.TopicNameIF
import net.ontopia.topicmaps.core.OccurrenceIF
import net.ontopia.topicmaps.query.core.QueryResultIF

import net.ontopia.groovy.Topic
import net.ontopia.groovy.Occurrence

class QueryResults {

  String[] columnNames
  Integer currentResult
  QueryResultIF ontopia_results

  QueryResults(QueryResultIF ont_results) {
    ontopia_results = ont_results
    columnNames = ontopia_results.getColumnNames()
    currentResult = 0
  }

  private groovify(val) {
    def value
    switch(val) {
    case { val == null } :
       value = null
       break
    case { val instanceof TopicIF } :
      value = new Topic(val)
      break
    case { val instanceof TopicNameIF } :
      value = val ?: val.getValue()
      break
    case { val instanceof OccurrenceIF } :
      value = new Occurrence(val)
      break
    case { val instanceof Integer } :
      value = val
      break
    case { val instanceof String } :
      value = val
      break
    default:
      value = val.getValue() ?: "other-null-value"
      break
    }
    value
  }

  def eachRow = { Closure closure ->
    def row
    for ( row = next(); row.size() > 0; row = next() ) {
      closure(row)
    }
  }

  ArrayList asList() {
   def results = []
   def row = []
   for ( row = next(); row.size() > 0; row = next() ) {
     results.add(row)
   }
   results
  }

  def next() {
    def row = [:]
    if ( ontopia_results.next() ) {
      currentResult++
      def row_values = ontopia_results.getValues()
      for ( int i=0; i < row_values.size(); i++) {
        row[ columnNames[i] ] = groovify(row_values[i])
      }
    }
    else {
      ontopia_results.close() // we're done with these
    }
    row
  }
}
