(ns staubzauberroboter.swing)

(import
 '(java.awt Color Graphics Dimension)
 '(javax.swing JPanel JFrame JButton JLabel)
 '(java.awt.event ActionListener))

(def catcher (atom nil))


(def frame (doto (new JFrame)
             (.add
              (doto (new JPanel)
                (.add (JLabel. "Hallo"))  ;;se agrega un JLabel al JPanel; lo que podria igualarse a un appendChild
                (.add
                 (doto (new JButton)
                   (.addActionListener
                    (proxy [java.awt.event.ActionListener] []
                      (actionPerformed [e] (swap! catcher (fn [_] e)))))))
                (.setPreferredSize (new Dimension
                                        300
                                        300))))
             .pack
             .show))

@catcher


