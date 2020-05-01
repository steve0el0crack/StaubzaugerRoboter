(ns staubzauberroboter.swing)

(import
 '(java.awt Color Graphics Dimension)
 '(javax.swing JPanel JFrame))

(def frame (doto (new JFrame) (.add
                               (doto (proxy [JPanel] [])
                                 (.setPreferredSize (new Dimension
                                                         300
                                                         300))))
                 .pack .show))


