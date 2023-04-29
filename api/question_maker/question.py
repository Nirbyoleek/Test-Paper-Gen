from transformers import AutoModelWithLMHead, AutoTokenizer
import spacy
import random

nlp = spacy.load('en_core_web_md')
tokenizer = AutoTokenizer.from_pretrained("mrm8488/t5-base-finetuned-question-generation-ap")
model = AutoModelWithLMHead.from_pretrained("mrm8488/t5-base-finetuned-question-generation-ap")


def get_question(answer, context, max_length=64):
  input_text = "answer: %s  context: %s </s>" % (answer, context)
  features = tokenizer([input_text], return_tensors='pt')

  output = model.generate(input_ids=features['input_ids'],
               attention_mask=features['attention_mask'],
               max_length=max_length)

  return tokenizer.decode(output[0])[16: -4]


def get_answers(context):
    doc = nlp(context)
    ans = []
    for ent in doc.ents:
        ans.append(ent.text)

    return random.sample(ans, min(10, len(ans)))


def get_questions(ans, context, max_length=64):
    q_arr = []
    for a in ans:
        q = get_question(a, context, max_length)
        q_arr.append(q)
    return q_arr


def Q_generation(context):
    a_arr = get_answers(context)
    q_arr = get_questions(a_arr, context)

    res = []
    for i in range(len(q_arr)):
        res.append({
            'question': q_arr[i],
            'answers': a_arr[i]
        })

    return res
