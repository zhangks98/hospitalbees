# HospitalBees
This is our group project for CE2006 Software Engineering course in Nanyang Technological University.

This repository host the Android App for HospitalBees. You may also have a look at our [Node.js backend](https://github.com/zhangks98/hospitalbee-node) as well as the [Clinic-side Queue Application](https://github.com/zhangks98/hospitalbees-qdemo)

## Introduction

Hospitalbees addresses the issue of having to travel all the way to the clinic and wait for a long queue by letting the user to make a booking beforehand. Making a booking on smartphone is equivalent to obtaining a queue number in person at the clinic. When the user arrives at the clinic and perform a check-in, the system will intelligently decide where to insert the queue.

Details on why and how this works can be found [here](https://github.com/zhangks98/hospitalbees-qdemo/wiki/Why-HospitalBees-works).

## Video Demo
stub


## Key features

* Make a booking
  * Total estimated waiting time allows user the freedom to plan their time
  * The ordering of available clinics are sorted based on total estimated waiting time
* View My Queue
  * The all-in-one place to view your current booking
  * Check-in / Reactivate a missed queue number with a simple scan of QR code
  * Get the number of patients in front of you in queue
  * Be notified timely
* View Booking History
* Alerts
  * View prevalent disease in Singapore

* What if people abuse the system with multiple bookings?
  * The queue is “actually” inserted only if the user is physically present at the clinic
  * Each user can only have one active booking in each clinic
  * Late arrival will have penalties
