import React from "react";
import styles from "./Header.module.css";
import { motion } from "framer-motion";
import { Link } from "react-router-dom";
import logo from "../../Assets/appLogo2.png";

const Header = (props) => {
	const { setQuestions } = props;
	return (
		<div className={styles.Nav}>
			<div className={styles.logoContainer}>
				<Link to="/home">
					<p id={styles.heading1}>
						<img src={logo} /> Gen
					</p>
				</Link>
			</div>
			<div className={styles.navBar}>
				<ul>
					<motion.li whileHover={{ scale: 1.04 }}>
						<Link
							className={styles.heading2}
							to="/home"
							onClick={() => {
								setQuestions(null);
							}}
						>
							Home
						</Link>
					</motion.li>
				</ul>
			</div>
		</div>
	);
};

export default Header;
