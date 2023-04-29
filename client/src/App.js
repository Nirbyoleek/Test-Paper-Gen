import logo from "./logo.svg";
import "./App.css";
import Landing from "../src/Components/Landing/Landing";
import Quiz from "./Components/Quiz/Quiz";

import Navbar from "./Components/NavbarMobile/NavbarMobile";
import PageNotFound from "./Components/404Page/404Page";
import {
	BrowserRouter as Router,
	Switch,
	Route,
	Redirect,
} from "react-router-dom";
import { useEffect, useState } from "react";
import { BsArrowClockwise } from "react-icons/bs";

function App() {
	const [questions, setQuestions] = useState(null);
	const [ans, setAns] = useState(null);
	const [theme, setTheme] = useState(true);

	useEffect(() => {
		console.log(questions);
	}, [questions]);

	return (
		<div className="App">
			<Router>
				<Switch>
					<Route exact path="/">
						<Redirect to="/home/" />
					</Route>
					<Route path="/home">
						{questions ? (
							<Redirect to="/Quiz" />
						) : (
							<Landing
								setQuestions={setQuestions}
								setAns={setAns}
								theme={theme}
							/>
						)}
					</Route>
					<Route path="/Quiz">
						{!questions ? (
							<Redirect to="/home/" />
						) : (
							<Quiz
								setQuestions={setQuestions}
								questions={questions}
								ans={ans}
								theme={theme}
							/>
						)}
					</Route>

					<Route>
						<PageNotFound setQuestions={setQuestions} theme={theme} />
					</Route>
				</Switch>
				<Navbar setQuestions={setQuestions} theme={theme} />
				<button
					className={theme ? "themeL_of_btn" : "theme_of_btn"}
					onClick={() => {
						setTheme(!theme);
					}}
				>
					<BsArrowClockwise />
				</button>
			</Router>
		</div>
	);
}

export default App;
