/**
 * 
 */
package jobs;

import helpers.PropertyVault;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import enumerations.CommentStatus;

import models.Comment;
import models.Restaurant;

import play.Logger;
import play.jobs.Job;
import play.jobs.On;

/**
 * @author Mike
 * 
 */
@On("0 4 * * * ?")
public class UpdateRatings extends Job {
    private static long MILLISECONDS_IN_DAY = 1000/* ms */* 60/* s */* 60/* m */* 24/* h */;

    @Override
    public void doJob() {
	String rrtg = PropertyVault.getSystemValueFor("ratingsRefreshTimeGap");

	Long gapInDays = null;
	if (rrtg != null) {
	    gapInDays = Long.parseLong(rrtg);
	} else {
	    Logger.warn("UpdateRatings Job: couldnot load 'ratingsRefreshTimeGap' system value dafaulting to 30 days");
	    gapInDays = 30L;
	}

	List<Comment> comments = Comment.find(
		"status = ? and (date between ? and ?) ",
		CommentStatus.APPROVED,
		new Date(),
		new Date(System.currentTimeMillis()
			- (gapInDays * MILLISECONDS_IN_DAY))).fetch();/*
								       * comments
								       * for
								       * last
								       * <getSystem
								       * value>
								       */
	if (comments != null && !comments.isEmpty()) {
	    Logger.info(
		    "UpdateRatings Job: starting to collect ratings for last %s days",
		    gapInDays);
	    Long startTime = System.currentTimeMillis();
	    LinkedHashMap<Restaurant, ArrayList<Integer>> map = new LinkedHashMap<Restaurant, ArrayList<Integer>>();
	    for (Comment comment : comments) {
		Restaurant rest = comment.restaurant;
		if (!map.containsKey(rest)) {
		    map.put(rest, new ArrayList<Integer>());
		}
		ArrayList<Integer> list = map.get(rest);
		list.add(comment.commonRating);
	    }
	    Logger.info(
		    "UpdateRatings Job: Collecting finished in %s s. Starting refresh...",
		    (System.currentTimeMillis() - startTime) / 1000F);
	    Iterator<Restaurant> iterator = map.keySet().iterator();
	    while (iterator.hasNext()) {
		Restaurant r = iterator.next();
		ArrayList<Integer> ratings = map.get(r);
		Integer totals = 0;
		for (Integer rating : ratings) {
		    totals += rating;
		}
		Integer average = totals / ratings.size();
		if (average > 5) {
		    average = 5;
		} else if (average < 1) {
		    average = 1;
		}
		r.raiting = average;
		r.save();
	    }
	    Logger.info(
		    "UpdateRatings Job: Finished refreshing (total time: %s s)",
		    (System.currentTimeMillis() - startTime) / 1000F);
	} else {
	    Logger.warn("UpdateRatings Job: No data to process!");
	}

    }
}
