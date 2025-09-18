# PalmChat - Fix Production Issue

---

## Test Screen:
<img width="1920" height="1080" alt="Image" src="https://github.com/user-attachments/assets/56ecba25-d049-436c-b9b9-7ccc9dfaa8bb" />

---

## Code Fix and explain:

The crash is caused by the fragment using requireActivity() as the LifecycleOwner for its LiveData observe in a fragment's lifecycle
the fragment's view is destroyed and recreated many times, but the parent activity's lifecycle continues.
When the user navigates away from the fragment and returns, the old fragment's view is destroyed and a new one is created however the old LiveData observer still bounded to the activity,
and continues to receive the new updates.
If a LiveData event is received when the fragment's view is gone but the activity still exists then the observer block is executed and when this block attempts to interact with the now destroyed recyclerView, it crashes as what happened.

---

## Code review issues and explanations:
- Issue: Using requireActivity() for LiveData observation..
Explanation: Hey, I noticed we're observing the viewModel.messages with the Activity's lifecycle. Fragments have a separate view lifecycle that's often shorter than the Activity's so if our fragment view is destroyed and then re-created
then the old observer attached to the Activity will still be active.
When the LiveData emits a new value, the old observer will still try to update a RecyclerView that no longer exists, causing a crash.
That's why We should use viewLifecycleOwner for UI related observations to prevent this.

- Issue: Direct RecyclerView adapter update inside the observer..
Explanation: It's a good start to update the adapter directly from the LiveData observer. However, this approach can be inefficient,
especially with large lists as each time new data comes in, we're creating and setting a brand new adapter, which is a heavy operation.
A more performant and cleaner approach is to use a ListAdapter and DiffUtil this will only update the items that have actually changed, leading to smoother UI performance.
We can make a separate ticket to refactor this, but it's something to keep in mind.

---
## Why not migrate to Flow/StateFlow yet?
Migrating to Flow and StateFlow is a significant refactoring effort as it involves changes across multiple layers like (ViewModel, Repository, etc.),
introduces new concepts like coroutines, and requires extensive re-testing. While beneficial for maintainability and scalability
so it is not a quick and low risk fix. For an urgent production crash, a minimal, targeted patch is the priority to restore stability. A full refactor would be a separate, longer term project.
