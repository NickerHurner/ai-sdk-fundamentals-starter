# AI SDK Tutorial (Android)

A modern Android application rewrite of the [Vercel AI SDK Tutorial Course](https://vercel.com/academy/ai-sdk), implemented with Kotlin, Jetpack Compose, Material 3, and Gemini API.

## Features & Lessons Ported

This application showcases the core patterns of building AI-native mobile applications:

### 1. Structured Extraction (Lesson 1)
- Extracts structured schemas and core arguments from Paul Graham's essay on *Founder Mode*.
- Compares Founder Mode vs Manager Mode characteristics side-by-side.
- Extracts key takeaways and notable quotations with one tap.

### 2. Classification & Triage (Lesson 2)
- Categorizes customer support requests into domain-specific categories (`billing`, `product_issues`, `enterprise_sales`, `account_issues`, `product_feedback`).
- Estimates triage urgency/priority levels (`urgent`, `high`, `medium`, `low`).
- Suggests automated response drafts for customer success agents.
- Category filtering and custom request classification.

### 3. Conversation Summarization (Lesson 3)
- Real-time discussion thread view with avatars and timestamps.
- AI summarization that condenses multi-person stakeholder discussions into structured `SummaryCard` components (Headline, Context, Key Discussion Points, Actionable Takeaways).
- Interactive comment thread with reply, like, and dislike support.

### 4. Natural Language Calendar Extraction (Lesson 4)
- Parses natural conversational sentences into structured `AppointmentDetails`.
- Extracts event title, date, start/end time, location, and attendee list with company email addresses.
- Renders calendar appointment cards with participant avatars and map markers.

### 5. Chatbot with Tool Calling (Lesson 5)
- Interactive conversational AI assistant.
- Real-time tool execution: detects weather queries and triggers local tool invocation to render live weather widgets (city, temperature, condition icons, and humidity).

## Architecture & Tech Stack

- **UI Framework:** Jetpack Compose with Material Design 3 (M3)
- **Language:** Kotlin 2.1
- **Networking:** Retrofit 2 + OkHttp 4
- **Serialization:** kotlinx.serialization JSON
- **AI Engine:** Google Gemini API (`gemini-3.5-flash`) with structured JSON schema responses and intelligent offline fallback
- **Navigation:** Navigation Compose
- **Design:** Custom adaptive app icon, Material You dynamic theming, edge-to-edge layout

## Configuration

Set your Gemini API key in `.env` or through the AI Studio Secrets panel:
```bash
GEMINI_API_KEY=your_key_here
```
