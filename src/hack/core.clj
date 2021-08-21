(ns hack.core
  (:import
   [boofcv.factory.tracker
    FactoryTrackerObjectQuad]

   [java.awt
    Graphics2D
    Color]
   [boofcv.gui.feature
    VisualizeFeatures]

   [boofcv.struct.image
    ImageBase]

   [java.awt.image
    BufferedImage]

   [boofcv.io.ffmpeg
    FfmpegVideoImageSequence]

   [boofcv.abst.tracker
    TrackerObjectQuad]

   [boofcv.io
    UtilIO
    MediaManager]

   [boofcv.io.image
    UtilImageIO]

   [boofcv.misc
    BoofMiscOps]

   [boofcv.io.wrapper
    DefaultMediaManager]

   [boofcv.gui.image
    ShowImages
    ImageGridPanel]

   [boofcv.struct.image
    GrayU8]

   [georegression.struct.shapes
    Quadrilateral_F64]

   [boofcv.gui.tracker
    TrackerObjectQuadPanel
    TrackerObjectQuadPanel$Listener]


   [java.awt
    Dimension]))


(defn open-video
  [file-name tracker]
  (let [media DefaultMediaManager/INSTANCE
        path  (UtilIO/pathExample file-name)]
    (->> (.getImageType tracker)
         (.openVideo media path))))


(defn create-tracker-panel
  [video frame default-location]
  (doto (TrackerObjectQuadPanel.
         (proxy [TrackerObjectQuadPanel$Listener] []
           (selectedTarget [q]
             (.set default-location q))
           (pauseTracker [& a]
             (first a))))
    (.setPreferredSize
     (Dimension. (.getWidth frame) (.getHeight frame)))
    (.setImageUI (.getGuiImage video))
    (.setTarget default-location true)))


(defn main [& _]
  (let [tracker
        (FactoryTrackerObjectQuad/circulant nil GrayU8)

        ^FfmpegVideoImageSequence video
        (open-video "example.mp4" tracker)

        location
        (Quadrilateral_F64.)

        frame
        (.next video)

        ^TrackerObjectQuadPanel gui
        (create-tracker-panel video frame location)]

    (do (dotimes [_ 300] (.next video)) (.next video))

    (ShowImages/showWindow gui "test" true)
    (Thread/sleep 10000)
    (.initialize tracker frame location)
    (while (.hasNext video)
      (let [^GrayU8 frame- (.next video)
            visible        (.process tracker frame- location)]
        (doto gui
          (.setTarget  location visible)
          (.setImageRepaint (.getGuiImage video)))
        ))
    nil))


(comment 
  (main))
