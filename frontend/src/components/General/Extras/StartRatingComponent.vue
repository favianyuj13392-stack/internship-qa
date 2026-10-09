<template>
    <div class="star-rating">
      <label 
        v-for="rating in ratings" 
        :key="rating" 
        class="star-rating__star" 
        :class="{ 'is-selected': (modelValue >= rating), 'is-disabled': disabled }" 
        @click="set(rating)" 
        @mouseover="starOver(rating)" 
        @mouseout="starOut"
      >
        <input 
          class="star-rating__checkbox" 
          type="radio" 
          :value="rating" 
          :name="name" 
          v-model="tempValue" 
          :disabled="disabled"
        >
        ★
      </label>
    </div>
  </template>
  
  <script>
  export default {
    name: 'StarRating',
    props: {
      name: String,
      modelValue: Number,
      disabled: Boolean,
      required: Boolean
    },
    data() {
      return {
        tempValue: this.modelValue,
        ratings: [1, 2, 3, 4, 5]
      };
    },
    watch: {
      modelValue(newVal) {
        this.tempValue = newVal;
      }
    },
    methods: {
      starOver(index) {
        if (!this.disabled) {
          this.tempValue = index;
        }
      },
      starOut() {
        if (!this.disabled) {
          this.tempValue = this.modelValue;
        }
      },
      set(value) {
        if (!this.disabled) {
          this.$emit('update:modelValue', value);
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .visually-hidden {
    position: absolute;
    overflow: hidden;
    clip: rect(0 0 0 0);
    height: 1px; width: 1px;
    margin: -1px; padding: 0; border: 0;
  }
  
  .star-rating {
    display: flex;
  }
  
  .star-rating__star {
    display: inline-block;
    padding: 3px;
    vertical-align: middle;
    line-height: 1;
    font-size: 1.5em;
    color: #ABABAB;
    transition: color .2s ease-out;
    cursor: pointer;
  }
  
  .star-rating__star.is-selected {
    color: #FFD700;
  }
  
  .star-rating__star.is-disabled:hover {
    cursor: default;
  }
  
  .star-rating__checkbox {
    position: absolute;
    overflow: hidden;
    clip: rect(0 0 0 0);
    height: 1px; width: 1px;
    margin: -1px; padding: 0; border: 0;
  }
  </style>
  