package net.ontopia.groovy

import net.ontopia.topicmaps.core.TopicIF
import net.ontopia.topicmaps.core.AssociationRoleIF
import net.ontopia.infoset.core.LocatorIF
import net.ontopia.infoset.core.Locators

/**
 * <p>Groovy facade that wraps a class that implements the Ontopia TopicIF interface</p>
 */

class Topic implements TopicIF {
  /**
   * delegate all unspecified methods to this object
   */
  @Delegate TopicIF delegate

  /* used to find the "untyped" topic name(s) associated with a topic */
  private static LocatorIF iso13250_name_si = Locators.getURILocator("http://psi.topicmaps.org/iso13250/model/topic-name")

  /**
   * constructs a groovy Topic from an object that implements the Ontopia TopicIF interface
   */
  Topic(TopicIF topic) {
    this.delegate = topic
  }

  /**
   * gets the occurrences of a topic
   */

  Collection<Occurrence> getOccurrences() {
    delegate.getOccurrences().collect{ new Occurrence(it) }
  }

  /**
   * gets the association roles that the topic plays
   */
  Collection<AssociationRole> getAssociationRoles() {
    delegate.getRoles().collect{ new AssociationRole(it) }
  }

  /**
   * gets the types of the topic
   */
  Collection<Topic> getTypes() {
    delegate.getTypes().collect { new Topic(it) }
  }

  /**
   * returns the string value of a name for the topic, preferring an untyped name
   */
  String toString() {
    def untyped_name = this.getTopicMap().getTopicBySubjectIdentifier(iso13250_name_si)
    def topic_names = delegate.getTopicNames().asList()
    if ( untyped_name ) {
      /* there is an "untyped-name" topic in the map */
      def untyped_names = topic_names.findAll { name ->
	def name_type = name.getType()
        ( name_type == null || name_type == untyped_name )
      }

      if ( untyped_names?.size() > 0 ) {
        untyped_names[0]?.value?.toString()    
      }
      else if ( topic_names?.size() > 0 ) {
	topic_names[0].value.toString()
      }
      else {
        "[No name]"
      }
    }
    else {
      println "Didn't find the untyped-name topic"
      /* there isn't an "untyped-name" topic in the map */
    }
  }
}
