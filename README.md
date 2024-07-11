# argmax_recognition

## Requirements
- Java: https://jdk.java.net/22/
- Python: 

## Execution
- Run `gradlew bootRun` at the repository root to launch the request serving layer service
- Run `src\main\python\inference_server.py` to launch the inference server

## Next steps
- Image retrieval connection headers and cert to avoid 403
- More graceful edge/corner case handling
    - Such as confidence score based filtering
- Unit tests