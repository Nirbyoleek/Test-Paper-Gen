import react, { useEffect, useState } from "react";
import axios from "axios";
import Header from "../Header/Header";
import Wave from "../../Assets/wave1.png";
import WaveL from "../../Assets/waveL.png";
import { AnimatePresence, motion } from "framer-motion";
import Intro from "./intro/intro";
import Uploader from "./uploader/uploader";
import { Route, useLocation, Switch, Redirect } from "react-router-dom";
import load from "../../Assets/load1.gif";
import logo from "../../Assets/appLogo2.png";
import logoL from "../../Assets/appLogo3.png";
import wavephone from "../../Assets/waves-phone.png";
import wavephoneL from "../../Assets/waves-phoneL.png";

const Landing = (props) => {
	const [file, setFile] = useState(null);
	const [text, setText] = useState(null);
	const [loader, setLoader] = useState(false);
	const location = useLocation();
	const [verify, setVerify] = useState(false);
	const [failModal, setModal] = useState(false);

	const { setQuestions, setAns, theme } = props;

	useEffect(() => {
		console.log(file);
	}, [file]);
	/*
    fetch('http://127.0.0.1:5000/upload').then(
        res=>console.log(res)
    )
*/

	//for text-extraction: https://testpapergen.herokuapp.com/

	const styles = theme
		? require("./LandingLight.module.css")
		: require("./Landing.module.css");

	const handleSubmit = () => {
		if (verify) {
			const formData = new FormData();
			setLoader(true);

			formData.append("name", "abc123");
			formData.append("key", text);

			fetch("http://localhost:5000/texttoquestion", {
				method: "POST",
				body: formData,
			})
				.then((result) => {
					result.json().then((res) => {
						var qa = res.data;
						var q = [];
						var a = [];
						console.log(res.data);
						qa.map((res) => {
							q.push(res.question);
							a.push(res.answers);
						});
						if (q.length > 0) {
							setQuestions(q);
							setAns(a);
						} else {
							setModal(true);
						}
						setLoader(false);
					});
				})
				.catch((error) => {
					console.error("Error:", error);
					setLoader(false);
				});
		} else {
			alert("verify you are not a robot");
		}
	};

	return (
		<div className={styles.majorContainer}>
			{/* <input
        type="file"
        onChange={(file) => {
          setFile(file.target.files[0]);
        }}
      />
      <button onClick={handleSubmit}>submits</button>*/}
			{loader && (
				<div className={styles.forAnimation}>
					<img src={load} className={styles.loading} />
				</div>
			)}
			<AnimatePresence exitBeforeEnter>
				{failModal && (
					<motion.div
						initial={{ opacity: 0 }}
						animate={{ opacity: 1 }}
						exit={{ opacity: 0 }}
						className={styles.modalBackdrop}
					>
						<motion.div
							initial={{ scale: 0 }}
							animate={{ scale: 1 }}
							exit={{ scale: 0 }}
							className={styles.modal}
						>
							<p>the system couldn't generate question from the given text</p>
							<button
								onClick={() => {
									setModal(false);
								}}
							>
								Close
							</button>
						</motion.div>
					</motion.div>
				)}
			</AnimatePresence>
			<Header setQuestions={setQuestions} />
			<div className={styles.main}>
				<div className={styles.introUploader}>
					{theme ? (
						<img className={styles.phnwv} src={wavephoneL} />
					) : (
						<img className={styles.phnwv} src={wavephone} />
					)}
					<AnimatePresence exitBeforeEnter>
						<Switch location={location} key={location.key}>
							<Route exact path="/home">
								<Intro theme={theme} />
							</Route>
							<Route path="/home/upload">
								<Uploader
									setFile={setFile}
									handleSubmit={handleSubmit}
									setText={setText}
									text={text}
									setLoader={setLoader}
									verify={verify}
									setVerify={setVerify}
									theme={theme}
								/>
							</Route>
							<Route>
								<Redirect TO="/home/" />
							</Route>
						</Switch>
					</AnimatePresence>
				</div>
				<div className={styles.logoContainer}>
					{theme ? (
						<img className={styles.logo} src={logoL} />
					) : (
						<img className={styles.logo} src={logo} />
					)}
					{theme ? (
						<img className={styles.waveCtn} src={WaveL} />
					) : (
						<img className={styles.waveCtn} src={Wave} />
					)}
				</div>
			</div>
		</div>
	);
};

export default Landing;
