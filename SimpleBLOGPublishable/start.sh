#!/bin/sh

readonly classpath=lib/commons-fileupload-1.4.jar:lib/jmtemplate.jar:lib/SimpleBLOGMiniHttpServer_0.1.jar:lib/commons-io-2.6.jar:lib/SimpleBLOGCore_0.1.jar:lib/SimpleBLOGWeb_0.1.jar
readonly workingDirectory="$(pwd)/workingDirectory"
readonly listenPort=65000
readonly listenHost=localhost
readonly urlPrefix=SimpleBLOG

SIMPLEBLOG_HOME=${workingDirectory}
export SIMPLEBLOG_HOME

java -cp ${classpath} com.simpleblog.minihttp.Server -port ${listenPort} -host ${listenHost} -prefix ${urlPrefix} 
