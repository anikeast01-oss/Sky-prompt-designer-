package com.example.data

import com.example.model.PromptTemplate

object TemplatesData {
    val templates = listOf(
        PromptTemplate(
            id = "youtube",
            title = "YouTube Content",
            subtitle = "Viral pacing, narrative hooks & retention",
            category = "Video & Creator",
            iconKey = "video",
            defaultTopic = "Create a high-retention YouTube video script about space exploration and James Webb discoveries",
            role = "YouTube Script Architect & Retention Strategist",
            audience = "General",
            tone = "Engaging & Dynamic",
            outputFormat = "Markdown Script with Visual & Audio Cues"
        ),
        PromptTemplate(
            id = "marketing",
            title = "Marketing Strategy",
            subtitle = "Acquisition loops, ICP & go-to-market",
            category = "Business & Growth",
            iconKey = "trending",
            defaultTopic = "Create an omnichannel launch strategy for an AI productivity app targeting remote workers",
            role = "Chief Marketing Officer & Growth Architect",
            audience = "Professional",
            tone = "Persuasive & Tactical",
            outputFormat = "Go-to-Market Playbook"
        ),
        PromptTemplate(
            id = "business_plan",
            title = "Business Plan",
            subtitle = "Unit economics, TAM & defensibility",
            category = "Business & Growth",
            iconKey = "briefcase",
            defaultTopic = "Draft a seed-stage business plan for a B2B SaaS supply chain optimization platform",
            role = "Venture Partner & Startup Operator",
            audience = "Investors & Executives",
            tone = "Strategic & Analytical",
            outputFormat = "Executive Summary & Financial Framework"
        ),
        PromptTemplate(
            id = "coding",
            title = "Coding Assistant",
            subtitle = "Architecture, clean code & security",
            category = "Engineering",
            iconKey = "code",
            defaultTopic = "Design a high-throughput event-driven microservices architecture using Kotlin and Kafka",
            role = "Staff Systems Architect & Clean Code Specialist",
            audience = "Engineers",
            tone = "Technical & Precise",
            outputFormat = "Architecture Document with Code Samples"
        ),
        PromptTemplate(
            id = "study_tutor",
            title = "Study Tutor",
            subtitle = "First-principles mental models & drills",
            category = "Education",
            iconKey = "school",
            defaultTopic = "Explain quantum entanglement to a college freshman using intuitive real-world analogies",
            role = "Distinguished Physics Professor & Socratic Tutor",
            audience = "Student",
            tone = "Educational & Patient",
            outputFormat = "Step-by-step Lesson with Self-Test Quizzes"
        ),
        PromptTemplate(
            id = "blog_writer",
            title = "Blog Writer",
            subtitle = "Compelling long-form storytelling & takeaways",
            category = "Content",
            iconKey = "edit",
            defaultTopic = "Write an insightful 1,500-word essay on how generative AI is reshaping software engineering careers",
            role = "Lead Tech Journalist & Editorial Essayist",
            audience = "Tech Enthusiasts",
            tone = "Thoughtful & Authoritative",
            outputFormat = "Long-form Markdown Article"
        ),
        PromptTemplate(
            id = "seo_content",
            title = "SEO Content",
            subtitle = "Search intent, keyword clusters & H2 schema",
            category = "Content",
            iconKey = "search",
            defaultTopic = "Generate an SEO content pillar page targeting 'best cloud database architecture for startups'",
            role = "Senior Technical SEO Strategist",
            audience = "Tech Decision Makers",
            tone = "Authoritative & Informative",
            outputFormat = "Structured SEO Article with Meta Tags & FAQ"
        ),
        PromptTemplate(
            id = "research",
            title = "Research Assistant",
            subtitle = "Literature review, methodology & synthesis",
            category = "Academic",
            iconKey = "science",
            defaultTopic = "Synthesize recent breakthroughs in solid-state battery energy density and commercialization barriers",
            role = "Principal Materials Science Research Fellow",
            audience = "Expert",
            tone = "Objective & Rigorous",
            outputFormat = "Literature Review Synthesis with Citations"
        ),
        PromptTemplate(
            id = "social_media",
            title = "Social Media",
            subtitle = "Hook writing, carousels & thread pacing",
            category = "Creator",
            iconKey = "share",
            defaultTopic = "Create a high-impact 7-part LinkedIn carousel and X thread analyzing the rise of agentic AI",
            role = "Viral Social Growth Copywriter",
            audience = "Professional",
            tone = "Crisp, Punchy & Viral",
            outputFormat = "Slide-by-Slide Carousel Deck & Thread"
        ),
        PromptTemplate(
            id = "image_gen",
            title = "Image Generation",
            subtitle = "Lighting, camera optics, composition & vibe",
            category = "Design",
            iconKey = "palette",
            defaultTopic = "A hyper-futuristic glass laboratory floating in deep space overlooking nebula rings, 8k resolution, cinematic lighting",
            role = "Cinematic Visual Director & Midjourney Prompt Artist",
            audience = "Designers",
            tone = "Descriptive & Evocative",
            outputFormat = "Midjourney / Imagen Parameterized Prompt"
        )
    )
}
