package net.ontopia.groovy

import net.ontopia.groovy.QueryResults

import net.ontopia.topicmaps.core.TopicMapIF
import net.ontopia.topicmaps.core.TopicMapReaderIF
import net.ontopia.topicmaps.utils.ImportExportUtils
import net.ontopia.topicmaps.query.utils.QueryUtils
import net.ontopia.topicmaps.query.core.QueryResultIF
import net.ontopia.topicmaps.entry.XMLConfigSource

/**
 * A class that facilitates performing tolog queries against a topic map 
 * (analogous to the Sql class in the gdk)
 *
 * @author Dan Speck
 * @date 2012-03-12
 */

class Tolog {
  /**
   * topic map to query
   */
  TopicMapIF topicmap
  private factory		// query processor factory
  private processor		// query processor
  private repository		// TM repository
  private store			// TM store

  /**
   * flag indicating the TM is read-only
   */
  Boolean isReadonly

  /**
   * Instantiates a Tolog object backed by a file-based topic map
   *
   * @param filename name of file containing topic map to query 
   */
  Tolog(String filename) {	
    def reader = ImportExportUtils.getReader(filename)
    isReadonly = true
    topicmap = reader.read()
    factory = QueryUtils.getQueryProcessorFactory("tolog")
    processor = factory.createQueryProcessor(topicmap,null,null)
  }

  /**
   * Instantiates a Tolog object backed by an RBDBMS topic map
   *
   * @param tmid topic map identifier string for RDBMS topic map
   * @param isReadonly flag indicating if this will be a readonly session
   */
  Tolog(String tmid, boolean isReadonly) {
    repository = XMLConfigSource.getRepositoryFromClassPath("tm-sources.xml")
    def tmref = repository.getReferenceByKey(tmid)
    // FIXME: this should really throw an exception
    if ( tmref == null ) {
      println "Couldn't locate topic map $tmid"
      return
    }
    store = tmref.createStore(isReadonly)
    topicmap = store.getTopicMap()
    store = topicmap.getStore()
    factory = QueryUtils.getQueryProcessorFactory("tolog")
    processor = factory.createQueryProcessor(topicmap,null,null)
  }

  /**
   * Executes the specified tolog query and returns a QueryResults object 
   * containing the results
   *
   * @param query tolog query to execute
   */
  QueryResults execute(String query) {
    def stmt = processor.parse(query)
    new QueryResults( stmt.execute() )
  }

  /**
   * Executes the specified tolog update query and returns the number of rows modified
   *
   * @param query tolog update query to execute
   */
  Integer update(String query) {
    def rows_updated = 0
    try {
      def stmt = processor.parseUpdate(query)
      rows_updated = stmt.update() 
      store.commit()
    }
    catch ( Exception e ) {
      println "Error executing update: $e"
      store.abort()
    }
    return rows_updated
  }

  /**
   * Executes the specified tolog query but returns only the number of tuples in the result set
   *
   * @param query tolog query to execute
   */
  Integer executeCountOnly(String query) {
    def stmt = processor.parse(query)
    def num_records = 0
    def results = new QueryResults(stmt.execute())
    while ( results.next() ) { num_records++ }
    num_records
  }

  def getStore() {
    return store
  }

  void finalize() {
    if (store?.isOpen()) {
      store.close()
    }
  }
}
