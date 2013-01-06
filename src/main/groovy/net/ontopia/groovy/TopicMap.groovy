package net.ontopia.groovy;

import net.ontopia.topicmaps.core.*

class TopicMap {
  @Delegate TopicMapIF delegate

  TopicMap(TopicMapIF tm) {
    this.delegate = tm
  }

  def invokeMethod(String method_name, args) {
    def value = delegate.invokeMethod(method_name, args)
    switch (value) {
    case TopicIF:		return new Topic(value)
    case OccurrenceIF:		return new Occurrence(value)
    case AssociationIF:		return new Association(value)
    case AssociationRoleIF:	return new AssociationRole(value)
    case TopicNameIF:		return new TopicName(value)
    default:			return value
    }
  }

  Collection<TopicIF> getTopics() {
    delegate.getTopics().collect{ new Topic(it) }
  }

  Collection<OccurrenceIF> getOccurrences() {
    delegate.getOccurrences().collect{ new Occurrence(it) }
  }

  Collection<AssociationIF> getAssociations() {
    delegate.getAssociations().collect{ new Association(it) }
  }

}
