from sklearn.feature_extraction.text import TfidfVectorizer,CountVectorizer
from sklearn.model_selection import train_test_split
import pandas as pd


body = ['The number of features in the language itself is modest, requiring relatively little investment of time or effort to produce your first programs.', 'The Python syntax is designed to be readable and straightforward.', 'This simplicity makes Python an ideal teaching language, and it lets newcomers pick it up quickly.', 'As a result, developers spend more time thinking about the problem they’re trying to solve and less time thinking about language complexities or deciphering code left by others.', 'Python is both popular and widely used, as the high rankings in surveys like the Tiobe Index and the large number of GitHub projects using Python attest.', 'Python runs on every major operating system and platform, and most minor ones too.', 'Many major libraries and API-powered services have Python bindings or wrappers, letting Python interface freely with those services or directly use those libraries.', 'The most basic use case for Python is as a scripting and automation language.', 'Python isn’t just a replacement for shell scripts or batch files; it is also used to automate interactions with web browsers or application GUIs or to do system provisioning and configuration in tools such as Ansible and Salt.', 'But scripting and automation represent only the tip of the iceberg with Python.']

train, test = train_test_split(body, test_size = 0.5, random_state=400)
print(train)
print(test)

cv=CountVectorizer(max_df=0.85,stop_words= 'english' ,max_features=10000)
word_count_vector = cv.fit_transform(body)
print(list(cv.vocabulary_.keys()))

tfidfvectorizer = TfidfVectorizer(analyzer='word',stop_words= 'english')
tfidfvectorizer.fit(word_count_vector)
# tfidf_train = tfidfvectorizer.transform(train)
# tfidf_term_vectors  = tfidfvectorizer.transform(test)
# print("Sparse Matrix form of test data : \n")
# print(tfidf_term_vectors)

# count_wm = countvectorizer.fit_transform(train)
# tfidf_wm = tfidfvectorizer.fit_transform(train)

# print(tfidf_wm)

# count_tokens = countvectorizer.get_feature_names()
# tfidf_tokens = tfidfvectorizer.get_feature_names()

