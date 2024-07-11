from flask import Flask, request

app = Flask(__name__)

@app.route('/run_inference', methods=["POST"])
def run_inference():
    body = request.form
    print(body)
    return ""

if __name__ == '__main__':
    app.run()
