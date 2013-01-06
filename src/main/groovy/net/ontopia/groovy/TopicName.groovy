package net.ontopia.groovy

import net.ontopia.topicmaps.core.TopicNameIF
import net.ontopia.topicmaps.core.TopicIF

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class TopicName implements TopicNameIF {
  @Delegate TopicNameIF delegate

  TopicName(TopicNameIF topic_name) {
    this.delegate = topic_name
  }

  TopicIF getTopic() {
    new Topic(delegate.getTopic())
  }

  Collection<TopicIF> getScope() {
    delegate.getScope().collect{ new Topic(it) }
  }

  String toString() {
    getValue()
  }
}
