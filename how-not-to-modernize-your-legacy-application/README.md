# How NOT to modernize your legacy application
- Never do big bang rewrites 
- Do small rewrites (f. e. stranger fig pattern)
- Don't neglect your legacy system
- Select your architecture by requirements (and not by consultants)
- Don't scale out teams without experience or plan
- Let one team get experience with the new architecture & release it as soon as possible
- Don't create teams - split teams!
- SAFe is not agile
- SAFe is burning money
- Create value stream teams and work really agile
- Use mocks for value and usability testing (and not the final application)
- Don't neglect your technical base because of Feature, Features, Features
- Start with the implementation of data imports early
- Don't promise impossible deadlines
- Learn to say no (yes, also to your boss)
- Don't say you will try
- Don't work insane hours
- Find compromises
- Don't go live with a broken
  - that misses half the data
  - where the other half of data is garbage
  - especially if you engineers tell you nine months ahead that this will happen
- Be a good manager
- Get started with test data early, especially when working with distributed systems
- QA and Test Automation is key
- Don't make the mistake of "Entity" Microservices
  - Size should not be your slicing choice
- The key benefit of Microservices is independent deployability
  - if you are not utilizing that benefit, then you are just paying the price for a distributed system

Probably project specific problems:
- BFF turned out as a new Big Ball of Mud
- Misinterpreting Least-Access-Privilege (Political issue)

Things we got right:
- A lot of automation (pipelines) and about 99% as infrastructure as code
- Dedicated UX & Design resources
  - use the wiremocks/click dummys for value & usability testing
- Templates are a key enabler for microservices (Standardization vs Autonomy)
- ArchUnit helps to keep multiple teams within your architecture (Standardization vs Autonomy)
- Some rudiments of value stream team (Backend, Frontend & QA within one team)

Key takeaways:
- Digital Transformation will required a shift in organisational structure
- Microservices are not only a technical pattern, but foremost an organisational structure
- Start small
- Release early
- Find a way to say yes, but learn to say no.
- Be agile
- Take care of your self

Quotes to finish
"Life is to short to work for bad managers"
"If a server CPU runs at 100%, we consider it a problem, because the system becomes unresponsive. Why don't we see it as a problem, if a team runs on 100%?"
