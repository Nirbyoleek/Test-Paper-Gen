import React, { useEffect, useState } from "react";
import Header from "../Header/Header";
import Wave from "../../Assets/wave1.png";
import WaveL from "../../Assets/waveL.png";
import { AnimatePresence, motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import arrow from '../../Assets/arrow.svg'
import logo from '../../Assets/appLogo2.png';
import logoL from '../../Assets/appLogo3.png';
import wavephone from '../../Assets/waves-phone.png'


const PageNotFound = (props) => {
    const {
        setQuestions,
        theme
    } = props

    const styles =theme? require("./PageLight.module.css"):require("./Page.module.css");

  return (
    <div className={styles.majorContainer} >
      <Header setQuestions={setQuestions}/>
      <div className={styles.main}>
        <div className={styles.introUploader}>
            <img className={styles.phnwv} src={wavephone}/>
            <div className={styles.info}>
                <motion.div 
                    className={styles.sym}
                    initial={{opacity:0}}
                    animate={{opacity:1,transition:{
                    duration:0.6
                    }}}
                    exit={{opacity:0,transition:{duration:0.4}}}
                >
                    <h2>4</h2>
                    {
                      theme?
                        <img className={styles.logo} src={logoL} /> :
                        <img className={styles.logo} src={logo} />
                    }
                    <h2>4</h2>
                </motion.div>
                <div className={styles.content}>
                    <p>Oops! The page that you are searching for was not found.</p>
                </div>
                <motion.div 
                    className={styles.buttonContainer}
                    initial={{x:-300,opacity:0}}
                    animate={{x:0,opacity:1,transition:{delay:0.3}}}
                    exit={{x:-800,opacity:0,transition:{duration:0.4}}}
                >
                    <Link to="/home">
                    <button>
                        BACK TO HOMEPAGE
                        <img src={arrow}/>
                    </button>
                    </Link>
                </motion.div>

            </div>
          
        </div>
        <div className={styles.logoContainer}>
          {
            theme?
            <img className={styles.waveCtn} src={WaveL}/> :
            <img className={styles.waveCtn} src={Wave}/>
          }
        </div>
      </div>
    </div>
  );
};

export default PageNotFound;
