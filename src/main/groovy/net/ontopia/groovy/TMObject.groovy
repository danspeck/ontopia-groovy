package net.ontopia.groovy

import net.ontopia.topicmaps.core.TMObjectIF

class TMObject {
  TMObjectIF delegate

  TMObject(TMObjectIF tmo) {
    this.delegate = tmo
  }

  boolean equals(Object other) {
    if ( null == other ) return false
    if ( other.delegate == delegate ) return true
    return false
  }

  int hashCode() {
    delegate.hashCode()
  }

  def invokeMethod(String method_name, args) {
    delegate.invokeMethod(method_name, args)
  }

  TopicMap getTopicMap() {
    new TopicMap(delegate.getTopicMap())
  }

  String getObjectId() {
    delegate.getObjectId()
  }

  def getItemIdentifiers() {
    delegate.getItemIdentifiers()
  }
}
