package com.jcu.focusgarden.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcu.focusgarden.data.repository.GroupRepository
import com.jcu.focusgarden.ui.screens.MemberProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Heist Group ViewModel
 * Implemented according to TD Document MVVM architecture
 * 
 * Week 3-4: Basic structure
 * Week 7-8: Simplified implementation with mock data
 */
class HeistViewModel(
    private val groupRepository: GroupRepository
) : ViewModel() {
    
    // Group State
    private val _groupGoal = MutableStateFlow("Complete 8 Pomodoros Today")
    val groupGoal: StateFlow<String> = _groupGoal.asStateFlow()
    
    private val _groupStreak = MutableStateFlow(3)
    val groupStreak: StateFlow<Int> = _groupStreak.asStateFlow()
    
    private val _members = MutableStateFlow<List<MemberProgress>>(emptyList())
    val members: StateFlow<List<MemberProgress>> = _members.asStateFlow()
    
    init {
        // TODO: Week 7-8 - Load group data from database
        loadGroupData()
    }
    
    /**
     * Load group data
     * Week 7-8: Updated to match new MemberProgress signature
     */
    private fun loadGroupData() {
        viewModelScope.launch {
            // Mock data (Week 7-8 simplified implementation)
            // MemberProgress(name, minutes, completed, target, initials)
            _members.value = listOf(
                MemberProgress("Alex Chen", 75, 5, 8, "AC"),
                MemberProgress("Sara Kim", 50, 4, 8, "SK"),
                MemberProgress("John Davis", 100, 6, 8, "JD"),
                MemberProgress("Emma Wilson", 45, 3, 8, "EW")
            )
        }
    }
    
    /**
     * Invite new member
     */
    fun inviteMember(memberName: String) {
        viewModelScope.launch {
            // TODO: Week 7-8 - Implement invite logic
        }
    }
    
    /**
     * Give thumbs up to member
     */
    fun thumbsUpMember(memberName: String) {
        viewModelScope.launch {
            // TODO: Week 7-8 - Implement thumbs up logic
        }
    }
    
    /**
     * Refresh group data
     */
    fun refreshGroupData() {
        loadGroupData()
    }
}





