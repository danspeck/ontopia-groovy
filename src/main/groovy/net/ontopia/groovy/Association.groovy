package net.ontopia.groovy

import net.ontopia.topicmaps.core.TopicIF
import net.ontopia.topicmaps.core.AssociationIF
import net.ontopia.topicmaps.core.AssociationRoleIF

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Association {
  /**
   * delegate all unspecified method calls to this object
   */
  @Delegate AssociationIF delegate

  Association(AssociationIF assoc) {
    this.delegate = assoc
  }

  Topic getType() {
    new Topic(delegate.getType())
  }

  Collection<AssociationRole> getRoles() {
    delegate.getRoles().collect { new AssociationRole(it) }
  }

  Collection<Topic> getRoleTypes() {
    delegate.getRoleTypes().collect { new Topic(it) }
  }

  Collection<AssociationRole>getRolesByType(role_type) {
    delegate.getRolesByType( role_type ).collect { new AssociationRole(it) }
  }

}

