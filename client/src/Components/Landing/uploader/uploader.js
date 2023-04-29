import React, { useCallback, useEffect, useState } from "react";
import { useDropzone } from "react-dropzone";
import { motion } from "framer-motion";
import wavephone from "../../../Assets/waves-phone.png";
import Recaptcha from "react-recaptcha";
import { FaFileUpload } from "react-icons/fa";

const Uploader = (props) => {
	const {
		setFile,
		handleSubmit,
		setText,
		text,
		setLoader,
		verify,
		setVerify,
		theme,
	} = props;

	const styles = theme
		? require("./uploaderLight.module.css")
		: require("./uploader.module.css");

	const siteOnLoad = () => {
		if (verify) {
			setVerify(false);
		} else {
			console.log("you are not verified");
		}
	};

	const onVerify = () => {
		if (!verify) {
			setVerify(true);
		}
	};

	const onExpire = () => {
		setVerify(false);
	};

	useEffect(() => {
		console.log(verify);
	}, [verify]);

	const onDrop = useCallback((acceptedFiles) => {
		//setFile(acceptedFiles[0]);
		//setExt(acceptedFiles[0]);
		setLoader(true);
		const formData = new FormData();

		formData.append("file", acceptedFiles[0]);

		fetch("http://localhost:5000/filetotext", {
			method: "POST",
			body: formData,
		})
			.then((result) => {
				result.json().then((res) => {
					var txt = res.data;
					console.log(txt);
					setText(txt);
					setLoader(false);
				});
			})
			.catch((error) => {
				console.error("Error:", error);
				setLoader(false);
			});
	}, []);
	const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

	return (
		<div className={styles.majorContainer}>
			<motion.div
				{...getRootProps()}
				className={isDragActive ? styles.dropBoxA : styles.dropBoxO}
				initial={{ scale: 0, opacity: 0 }}
				animate={{ scale: 1, opacity: 1 }}
				exit={{ scale: 0, opacity: 0, transition: { duration: 0.3 } }}
			>
				<input {...getInputProps()} accept=".pdf" />
				{isDragActive ? (
					<p>
						<a></a>Drop the files here ...<a></a>
					</p>
				) : (
					<p>
						<FaFileUpload />
						Upload PDF <a></a>
					</p>
				)}
			</motion.div>
			<motion.div
				className={styles.textpreview}
				initial={{ scale: 0, opacity: 0 }}
				animate={{ scale: 1, opacity: 1 }}
				exit={{ scale: 0, opacity: 0, transition: { duration: 0.3 } }}
			>
				<textarea
					value={text}
					onChange={(e) => {
						setText(e.target.value);
					}}
					placeholder={
						isDragActive
							? "The text will be extracted from the file"
							: "Enter text to generate questions"
					}
				></textarea>
			</motion.div>
			<motion.div
				className={styles.buttonContainer}
				initial={{ opacity: 0 }}
				animate={{ opacity: 1, transition: { delay: 0.3 } }}
				exit={{ opacity: 0, transition: { duration: 0.4 } }}
			>
				<center>
					<Recaptcha
						sitekey="6LeiIZMbAAAAADK4NrnlIoLqnH96FWbWWpssgB2T"
						render="explicit"
						onloadCallback={siteOnLoad}
						verifyCallback={onVerify}
						expiredCallback={onExpire}
						size="normal"
					/>
					<motion.button
						whileHover={{ scale: 1.03, transition: { duration: 0.1 } }}
						onClick={handleSubmit}
						disabled={!text}
					>
						GENERATE QUESTIONS
					</motion.button>
				</center>
			</motion.div>
		</div>
	);
};

export default Uploader;
