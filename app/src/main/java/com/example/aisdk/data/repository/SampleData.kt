package com.example.aisdk.data.repository

import com.example.aisdk.data.model.CommentAuthor
import com.example.aisdk.data.model.CommentMessage
import com.example.aisdk.data.model.SupportTicket

object SampleData {

    val ESSAY_TEXT = """
        September 2024
        
        At a YC event last week Brian Chesky gave a talk that everyone who was there will remember. Most founders I talked to afterward said it was the best they'd ever heard. Ron Conway, for the first time in his life, forgot to take notes. I'm not going to try to reproduce it here. Instead I want to talk about a question it raised.
        
        The theme of Brian's talk was that the conventional wisdom about how to run larger companies is mistaken. As Airbnb grew, well-meaning people advised him that he had to run the company in a certain way for it to scale. Their advice could be optimistically summarized as "hire good people and give them room to do their jobs." He followed this advice and the results were disastrous. So he had to figure out a better way on his own, which he did partly by studying how Steve Jobs ran Apple. So far it seems to be working. Airbnb's free cash flow margin is now among the best in Silicon Valley.
        
        The audience at this event included a lot of the most successful founders we've funded, and one after another said that the same thing had happened to them. They'd been given the same advice about how to run their companies as they grew, but instead of helping their companies, it had damaged them.
        
        Why was everyone telling these founders the wrong thing? That was the big mystery to me. And after mulling it over for a bit I figured out the answer: what they were being told was how to run a company you hadn't founded — how to run a company if you're merely a professional manager. But this m.o. is so much less effective that to founders it feels broken. There are things founders can do that managers can't, and not doing them feels wrong to founders, because it is.
        
        In effect there are two different ways to run a company: founder mode and manager mode. Till now most people even in Silicon Valley have implicitly assumed that scaling a startup meant switching to manager mode. But we can infer the existence of another mode from the dismay of founders who've tried it, and the success of their attempts to escape from it.
        
        The way managers are taught to run companies seems to be like modular design in the sense that you treat subtrees of the org chart as black boxes. You tell your direct reports what to do, and it's up to them to figure out how. But you don't get involved in the details of what they do. That would be micromanaging them, which is bad.
        
        Hire good people and give them room to do their jobs. Sounds great when it's described that way, doesn't it? Except in practice, judging from the report of founder after founder, what this often turns out to mean is: hire professional fakers and let them drive the company into the ground.
        
        Whatever founder mode consists of, it's pretty clear that it's going to break the principle that the CEO should engage with the company only via his or her direct reports. "Skip-level" meetings will become the norm instead of a practice so unusual that there's a name for it.
    """.trimIndent()

    val INITIAL_SUPPORT_REQUESTS = listOf(
        SupportTicket(1, "I'm having trouble logging into my account. Can you please assist?", "account_issues", "high", "Please click 'Forgot Password' on the login screen or verify your email."),
        SupportTicket(2, "The export feature isn't working correctly. Is there a known issue?", "product_issues", "medium", "We are investigating CSV export formatting for non-ASCII characters."),
        SupportTicket(3, "I need help integrating your API with our existing system.", "product_feedback", "medium", "Check out our REST API docs at https://docs.example.com/api"),
        SupportTicket(4, "Can you provide more information about your enterprise pricing plans?", "enterprise_sales", "high", "Our enterprise specialist team can schedule a dedicated demo."),
        SupportTicket(5, "I'm having trouble cancelling my account. Please can you help?", "billing", "urgent", "You can cancel under Settings > Subscription or reply to confirm cancellation."),
        SupportTicket(6, "The dashboard is not displaying real-time data. How can I fix this?", "product_issues", "high", "Ensure WebSockets are permitted on your corporate firewall."),
        SupportTicket(7, "Do you offer technical support for self-hosted installations?", "enterprise_sales", "low", "Self-hosted support is available with our Enterprise SLA package.")
    )

    val INITIAL_COMMENTS = listOf(
        CommentMessage(
            id = 1,
            author = CommentAuthor("Olivia Davis", "OD"),
            timestamp = "2 hours ago",
            content = "What's the update re the client feedback on the latest design?",
            likes = 3
        ),
        CommentMessage(
            id = 2,
            author = CommentAuthor("James Smith", "JS"),
            timestamp = "1 hour ago",
            content = "Client is happy with the design changes. We can proceed to the next phase.",
            likes = 5
        ),
        CommentMessage(
            id = 3,
            author = CommentAuthor("Emma Thompson", "ET"),
            timestamp = "45 minutes ago",
            content = "Great to hear! Do we have an updated timeline for the next deliverables?",
            likes = 2
        ),
        CommentMessage(
            id = 4,
            author = CommentAuthor("Liam Johnson", "LJ"),
            timestamp = "40 minutes ago",
            content = "I'm working on that. Should have something by end of day.",
            likes = 4
        ),
        CommentMessage(
            id = 5,
            author = CommentAuthor("Sophia Martinez", "SM"),
            timestamp = "30 minutes ago",
            content = "I'll need the finalized timeline to plan out the marketing strategy.",
            likes = 1
        ),
        CommentMessage(
            id = 6,
            author = CommentAuthor("Olivia Davis", "OD"),
            timestamp = "25 minutes ago",
            content = "Liam, do you need any help with the timeline?",
            likes = 2
        ),
        CommentMessage(
            id = 7,
            author = CommentAuthor("Liam Johnson", "LJ"),
            timestamp = "20 minutes ago",
            content = "Thanks Olivia, but I think I've got it under control. I'll let you know if anything changes.",
            likes = 3
        ),
        CommentMessage(
            id = 8,
            author = CommentAuthor("Emma Thompson", "ET"),
            timestamp = "15 minutes ago",
            content = "Quick question: has anyone reached out to the vendor about the new software licenses?",
            likes = 1
        ),
        CommentMessage(
            id = 9,
            author = CommentAuthor("James Smith", "JS"),
            timestamp = "10 minutes ago",
            content = "I can handle that. I'll send them an email today.",
            likes = 4
        ),
        CommentMessage(
            id = 10,
            author = CommentAuthor("Sophia Martinez", "SM"),
            timestamp = "8 minutes ago",
            content = "Thanks, James. Let us know what they say.",
            likes = 2
        ),
        CommentMessage(
            id = 11,
            author = CommentAuthor("Olivia Davis", "OD"),
            timestamp = "5 minutes ago",
            content = "Just a reminder: we have the client call tomorrow at 3 PM. Please be prepared with your updates.",
            likes = 6
        )
    )
}
