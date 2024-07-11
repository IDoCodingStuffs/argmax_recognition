from flask import Flask, request
from transformers import AutoImageProcessor, ResNetForImageClassification
import torch
import json

# !TODO: Local caching
processor = AutoImageProcessor.from_pretrained("microsoft/resnet-50")
model = ResNetForImageClassification.from_pretrained("microsoft/resnet-50")

app = Flask(__name__)

@app.route('/run_inference', methods=["POST"])
def run_inference():
    image_blob = request.form
    if image_blob:
        # !TODO: The actual transformation to a PIL image
        inputs = processor(image_blob, return_tensors="pt")
        with torch.no_grad():
            logits = model(**inputs).logits

        predicted_label = logits.argmax(-1).item()

        return json.dumps({"predicted_label": predicted_label})
    else:
        return json.dumps({"predicted_label": ""})
    
if __name__ == '__main__':
    app.run()
