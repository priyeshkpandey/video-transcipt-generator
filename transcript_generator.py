import sys
import whisper

model_size = "tiny"

def transcript_from_audio(audio_file):
    print("Creating model", model_size)
    model = whisper.load_model(model_size)
    print("Generating result")
    result = model.transcribe(audio_file, verbose=True)
    print("Saving result in file")
    with open(audio_file + "-transcript.txt", "w", encoding="utf-8") as file:
        file.write(result["text"])

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Video URL is required")
    else:
        transcript_from_audio(sys.argv[1])