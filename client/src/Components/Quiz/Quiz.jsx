import React, { useEffect, useState } from "react";
import Header from "../Header/Header";
import Fade from "react-reveal/Fade";
import { AnimatePresence, motion } from 'framer-motion';
const Quiz = (props) => {
  const [result, setResult] = useState(null);
  const [anskey,setanskey] = useState(false);

  const { setQuestions, questions, ans, theme } = props;


  const styles =theme? require("./QuizLight.module.css"):require("./Quiz.module.css");

  const Evaluate = (e) => {
    e.preventDefault();
    var l = ans.length;
    var marks = 0;

    for (var a = 0; a < l; a++) {
      var actualAns = ans[a];
      var givenAns = e.target.elements[a].value;
      if (actualAns.toLowerCase() === givenAns.toLowerCase()) {
        marks = marks + 1;
      }
    }

    setResult(marks);
  };

  useEffect(()=>{
    console.log(result);
  },[result])
  useEffect(()=>{
    console.log(ans);
  },[ans])

  return (
    <div className={styles.Main}>
      <Header setQuestions={setQuestions}/>
      <div className={styles.Container}>
        <Fade>
          <h1 id={styles.heading2}>Questions</h1>
        </Fade>
        <AnimatePresence exitBeforeEnter>
        {
          result===0 && 
              <motion.div
                initial={{opacity:0}}
                animate={{opacity:1}}
                exit={{opacity:0}} 
                className={styles.modalBackdrop}
              >
                <motion.div 
                  initial={{scale:0}}
                  animate={anskey?{scale:1,y:-100}:{scale:1}}
                  exit={{scale:0}} 
                  className={styles.modal}>
                  <h2>RESULTS</h2>
                  <p>SCORE {result}</p>
                  {
                    anskey?
                    <button onClick={()=>{setanskey(false)}}>HIDE</button>:
                    <button onClick={()=>{setanskey(true)}}>ANSWER KEY</button>
                  }
                  <button onClick={()=>{setQuestions(null)}}>BACK TO HOME</button>
                </motion.div>
                <motion.div 
                  initial={{scale:0}}
                  animate={anskey?{scale:1,y:-100,height:"60vh"}:{scale:1}}
                  exit={{scale:0}} 
                  className={styles.answerkey}>
                  <p>Answer Key</p>
                  <ul>
                    {
                      ans.map(an=>(
                        <li>{an}</li>
                      ))
                    }
                  </ul>
                </motion.div>
              </motion.div>
        }
        {
          result>0 && 
              <motion.div
                initial={{opacity:0}}
                animate={{opacity:1}}
                exit={{opacity:0}} 
                className={styles.modalBackdrop}
              >
                <motion.div 
                  initial={{scale:0}}
                  animate={anskey?{scale:1,y:-100}:{scale:1}}
                  exit={{scale:0}} 
                  className={styles.modal}>
                  <h2>RESULTS</h2>
                  <p>SCORE {result}</p>
                  {
                    anskey?
                    <button onClick={()=>{setanskey(false)}}>HIDE</button>:
                    <button onClick={()=>{setanskey(true)}}>ANSWER KEY</button>

                  }
                  <button onClick={()=>{setQuestions(null)}}>BACK TO HOME</button>
                </motion.div>
                <motion.div 
                  initial={{scale:0}}
                  animate={anskey?{scale:1,y:-100,height:"60vh"}:{scale:1}}
                  exit={{scale:0}} 
                  className={styles.answerkey}>
                  <p>Answer Key</p>
                  <ul>
                    {
                      ans.map(an=>(
                        <li>{an}</li>
                      ))
                    }
                  </ul>
                </motion.div>
              </motion.div>
        }
        </AnimatePresence>


        <form onSubmit={Evaluate}>
          {
            questions &&
            questions.map(
              data=>(
                <Fade bottom>
                  <div className={styles.Element}>
                    <div className={styles.Question}>
                      {data}
                    </div>
                    <p id={styles.Answer}>Ans:</p>
                    <input className={styles.Input} type="text" />
                  </div>
                </Fade>
              )
            )
          }
          <Fade bottom>
            <div>
              <button id={styles.Submit}>Submit</button>
            </div>
          </Fade>
        </form>
      </div>
    </div>
  );
};
export default Quiz;
