import spock.lang.*

import net.ontopia.groovy.Tolog

class TologSpec extends Specification {

  String tm_file = "src/test/resources/simple.ltm"

  def Tolog tolog = new Tolog(tm_file)

  def 'there should be two topics named Topic 1 and Topic 2 in the simple.ltm topic map'() {

  when:
    def topicResultSet = tolog.execute('select $T from topic($T)?')

    then:

    topicResultSet != null
    
    def tnames = topicResultSet.asList().collect { it.T.toString() }

    tnames.contains("Topic 1")
    tnames.contains("Topic 2")

  }

}