package net.ontopia.groovy

import net.ontopia.topicmaps.core.TopicIF
import net.ontopia.topicmaps.core.AssociationIF
import net.ontopia.topicmaps.core.AssociationRoleIF

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class AssociationRole {

  @Delegate
  AssociationRoleIF delegate

  AssociationRole(AssociationRoleIF role) {
    this.delegate = role
  }

  TopicIF getPlayer() {
    new Topic(delegate.getPlayer())
  }

  TopicIF getType() {
    new Topic(delegate.getType())
  }

  AssociationIF getAssociation() {
    new Association(delegate.getAssociation())
  }
}

