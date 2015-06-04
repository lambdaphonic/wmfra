(defproject lambdaphonic.wmfra "0.1.0-SNAPSHOT"
  :description "Live coding @ WebMontag Frankfurt, on 2015/06/08"

  :url "http://github.com/lambdaphonic/wmfra"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [overtone "0.9.1"]
                 [mud "0.1.0-SNAPSHOT"]
                 [leipzig "0.8.1"]
                 [shadertone "0.2.4"]]

  :jvm-opts [
    "-Xms512m" "-Xmx1g"           ; Minimum and maximum sizes of the heap
    "-XX:+UseParNewGC"            ; Use the new parallel GC in conjunction with
    "-XX:+UseConcMarkSweepGC"     ;  the concurrent garbage collector
    "-XX:+CMSConcurrentMTEnabled" ; Enable multi-threaded concurrent gc work (ParNewGC)
    "-XX:MaxGCPauseMillis=20"     ; Specify a target of 20ms for max gc pauses
    "-XX:+CMSIncrementalMode"     ; Do many small GC cycles to minimize pauses
    "-XX:MaxNewSize=257m"         ; Specify the max and min size of the new
    "-XX:NewSize=256m"            ;  generation to be small
    "-XX:+UseTLAB"                ; Uses thread-local object allocation blocks. This
                                  ;  improves concurrency by reducing contention on
                                  ;  the shared heap lock.
    "-XX:MaxTenuringThreshold=0"  ; Makes the full NewSize available to every NewGC
                                  ;  cycle, and reduces the pause time by not
                                  ;  evaluating tenured objects. Technically, this
                                  ;  setting promotes all live objects to the older
                                        ;  generation, rather than copying them.
    ;; "-XX:ConcGCThreads=2"        ; Use 2 threads with concurrent gc collections
    ;; "-XX:TieredCompilation"      ; JVM7 - combine both client and server compilation
    ;;                               ;  strategies
    ;; "-XX:CompileThreshold=1"     ; JIT each function after one execution
    ;; "-XX:+PrintGC"               ; Print GC info to stdout
    ;; "-XX:+PrintGCDetails"        ;  - with details
    ;; "-XX:+PrintGCTimeStamps"     ;  - and timestamps)
])
