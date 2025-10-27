from flask import Flask, request, jsonify
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer
from flask_cors import CORS


app = Flask(__name__)
CORS(app)

@app.route('/analyze', methods=['POST'])
def analyze():
    # For Phase 1, just return dummy data
    dummy_response = {
        "compound": 0.5,
        "positive": 0.3,
        "negative": 0.1,
        "neutral": 0.6,
        "label": "POSITIVE"
    }
    return jsonify(dummy_response)

if __name__ == '__main__':
    app.run(port=5000)