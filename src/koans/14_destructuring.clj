(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
  "Destructuring is an arbiter: it breaks up arguments"
  (= ":bar:foo" ((fn [[a b]] (str b a))
         [:foo :bar]))

  "Whether in function definitions"
  (= (str "First comes love, "
          "then comes marriage, "
          "then comes Clojure with the baby carriage")
     ((fn [[a b c]] (format (str "First comes %s, "
                                  "then comes %s, "
                                  "then comes %s with the baby carriage")
                            a b c
                    ))
      ["love" "marriage" "Clojure"]))

  "Or in let expressions"
  (= "Rich Hickey aka The Clojurer aka Go Time aka Macro Killah"
     (let [[first-name last-name & aliases]
           (list "Rich" "Hickey" "The Clojurer" "Go Time" "Macro Killah")]
       (apply str
                  (interpose " "
                    (apply list
                      first-name
                      last-name
                      (interleave (repeat "aka") aliases))))
     ))

  "You can regain the full argument if you like arguing"
  (= {:original-parts ["Steven" "Hawking"] :named-parts {:first "Steven" :last "Hawking"}}
     (let [[first-name last-name :as full-name] ["Steven" "Hawking"]]
       {:original-parts full-name :named-parts {:first first-name :last last-name}}))

  "Break up maps by key"
  (= "123 Test Lane, Testerville, TX"
     (let [{street-address :street-address, city :city, state :state} test-address]
       (str street-address ", " city ", " state)))

  "Or more succinctly"
  (= "123 Test Lane, Testerville, TX"
     (let [{:keys [street-address city state]} test-address]
       (str street-address ", " city ", " state)))

  "All together now!"
  (= "Test Testerson, 123 Test Lane, Testerville, TX"
     (
       (fn [[firstname lastname]
            {:keys [street-address city state]}]
          (str firstname " " lastname ", "
           street-address ", " city ", " state))
       ["Test" "Testerson"] test-address)))
