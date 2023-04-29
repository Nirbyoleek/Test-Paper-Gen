import React from 'react';
import {motion, AnimatePresence} from 'framer-motion';
import arrow from '../../../Assets/arrow.svg';
import { Link } from 'react-router-dom';
import wavephone from '../../../Assets/waves-phone.png'
import logo from '../../../Assets/appLogo2.png'
import logoL from '../../../Assets/appLogo3.png'


const Intro = (props)=>{

    const {
      theme
    } = props;

    const styles =theme? require('./introLight.module.css'):require('./intro.module.css');


    return(
        <motion.div className={styles.Mintro}>
          {
            theme?
            <img className={styles.logo} src={logoL} /> :
            <img className={styles.logo} src={logo} />
          }
            <motion.div 
            className={styles.intro}
            initial={{opacity:0}}
            animate={{opacity:1,transition:{
              duration:0.6
            }}}
            exit={{opacity:0,transition:{duration:0.4}}}
          >
            <h1>
              Test<br/>Paper<br/>Generator
            </h1>
            <h2>
              Test Paper<br /> Generator
            </h2>
            <p>
              Our system generates questions based on the pdf that you have uploaded. Just upload your pdf and voila you now have questions
            </p>
          </motion.div>
          <motion.div 
            className={styles.buttonContainer}
            initial={{x:-300,opacity:0}}
            animate={{x:0,opacity:1,transition:{delay:0.3}}}
            exit={{x:-800,opacity:0,transition:{duration:0.4}}}
          >
            <Link to="/home/upload">
              <button>
                LETS GET STARTED
                <img src={arrow}/>
              </button>
            </Link>
          </motion.div>
        </motion.div>
    )
}

export default Intro;