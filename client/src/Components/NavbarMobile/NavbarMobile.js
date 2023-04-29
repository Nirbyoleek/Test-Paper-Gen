import React from "react";
import { FaUser } from "react-icons/fa";
import { AiFillHome, AiOutlinePlus } from "react-icons/ai";
import { Link } from "react-router-dom";

const NavbarMobile = (props) => {
	const { setQuestions, theme } = props;

	const Styles = theme
		? require("./NavbarMobileLight.module.css")
		: require("./NavbarMobile.module.css");

	return (
		<div className={Styles.majorContainer}>
			<div className={Styles.btnCtn}>
				<Link
					to="/home/upload"
					onClick={() => {
						setQuestions(null);
					}}
				>
					<AiOutlinePlus />
				</Link>
			</div>
			<div className={Styles.container}>
				<Link
					to="/home/"
					onClick={() => {
						setQuestions(null);
					}}
				>
					<div className={Styles.home}>
						<AiFillHome />
					</div>
				</Link>
			</div>
		</div>
	);
};

export default NavbarMobile;
